package nguye.postcrunch.backend.post;

import nguye.postcrunch.backend.model.Post;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PostRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<PostEntity, Post> {

  public PostRepresentationModelAssembler() {
    super(PostController.class, Post.class);
  }

  @Override
  public Post toModel(PostEntity entity) {
    Post resource = createModelWithId(entity.getId(), entity)
        .id(entity.getId())
        .contentType(Post.ContentTypeEnum.valueOf(entity.getContentType()))
        .createdAt(Timestamp.valueOf(entity.getCreatedAt()))
        .updatedAt(Timestamp.valueOf(entity.getUpdatedAt()))
        .author(entity.getAuthor().getId())
        .title(entity.getTitle())
        .text(entity.getText());

    return resource;
  }
}
