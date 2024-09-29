package nguye.postcrunch.backend.post;

import nguye.postcrunch.backend.AppUtil;
import nguye.postcrunch.backend.comment.CommentRepresentationModelAssembler;
import nguye.postcrunch.backend.comment.CommentService;
import nguye.postcrunch.backend.model.Comment;
import nguye.postcrunch.backend.model.Post;
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
public class PostRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<PostEntity, Post> {

  private final CommentRepresentationModelAssembler commentRepresentationModelAssembler;
  private final CommentService service;

  public PostRepresentationModelAssembler(CommentRepresentationModelAssembler commentRepresentationModelAssembler, CommentService service) {
    super(PostController.class, Post.class);
    this.commentRepresentationModelAssembler = commentRepresentationModelAssembler;
    this.service = service;
  }

  @Override
  public Post toModel(PostEntity entity) {
    Post resource = new Post();
    resource.add(linkTo(methodOn(PostController.class).getPostById(entity.getId())).withSelfRel());

    List<Comment> comments = commentRepresentationModelAssembler.toListModel(
        service.getCommentsByContentId(entity.getId())
    );

    Timestamp createdAt = entity.getCreatedAt();
    Timestamp updatedAt = entity.getUpdatedAt();

    if (Objects.nonNull(entity.getAuthor())) {
      resource.setAuthor(AppUtil.extractAuthorInfo(entity.getAuthor()));
    }

    return resource
        .id(entity.getId())
        .contentType(Post.ContentTypeEnum.valueOf("POST"))
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .edited(!createdAt.equals(updatedAt))
        .title(entity.getTitle())
        .text(entity.getText())
        .numComments(comments.size())
        .comments(AppUtil.toCommentPreview(comments));
  }

  public List<Post> toListModel(Iterable<PostEntity> entities) {
    if (Objects.isNull(entities)) {
      return List.of();
    }
    return StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
        .collect(toList());
  }
}
