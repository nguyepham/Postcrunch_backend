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

  public CommentRepresentationModelAssembler() {
    super(CommentController.class, Comment.class);
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

    Timestamp createdAt = entity.getCreatedAt();
    Timestamp updatedAt = entity.getUpdatedAt();

    return resource
        .id(entity.getId())
        .contentType(Comment.ContentTypeEnum.COMMENT)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .author(AppUtil.extractAuthorInfo(entity.getAuthor()))
        .targetId(entity.getTarget().getId())
        .edited(!createdAt.equals(updatedAt))
//        .numComments(replies.size())
        .text(entity.getText());
  }

  public List<Comment> toListModel(Iterable<CommentEntity> entities) {
    if (Objects.isNull(entities)) {
      return List.of();
    }
    return StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
        .collect(toList());
  }
}
