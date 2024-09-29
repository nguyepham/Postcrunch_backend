package nguye.postcrunch.backend.comment;

import nguye.postcrunch.backend.AppUtil;
import nguye.postcrunch.backend.model.Comment;
import nguye.postcrunch.backend.post.PostController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<CommentEntity, Comment> {
  
  private final CommentService service;

  public CommentRepresentationModelAssembler(CommentService service) {
    super(CommentController.class, Comment.class);
    this.service = service;
  }

  @Override
  public Comment toModel(CommentEntity entity) {
    Comment resource = new Comment();
    resource.add(linkTo(methodOn(CommentController.class).getCommentById(entity.getId())).withSelfRel());

    switch (entity.getTarget().getContentType()) {
      case "POST" -> resource.add(
          linkTo(methodOn(PostController.class)
              .getPostById(entity.getTarget().getId())).withRel("target"));
      case "COMMENT" -> resource.add(
          linkTo(methodOn(CommentController.class)
              .getCommentById(entity.getTarget().getId())).withRel("target"));
    }

    List<Comment> replies = toListModel(
        service.getCommentsByContentId(entity.getId())
    );

    Timestamp createdAt = entity.getCreatedAt();
    Timestamp updatedAt = entity.getUpdatedAt();

    return resource
        .id(entity.getId())
        .contentType(Comment.ContentTypeEnum.valueOf("COMMENT"))
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .author(AppUtil.extractAuthorInfo(entity.getAuthor()))
        .target(entity.getTarget().getId())
        .edited(!createdAt.equals(updatedAt))
        .text(entity.getText())
        .numComments(replies.size())
        .comments(AppUtil.toCommentPreview(replies));
  }

  public List<Comment> toListModel(Iterable<CommentEntity> entities) {
    if (Objects.isNull(entities)) {
      return List.of();
    }
    return StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
        .collect(toList());
  }
}
