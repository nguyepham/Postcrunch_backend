package nguye.postcrunch.backend.post;

import nguye.postcrunch.backend.AppUtil;
import nguye.postcrunch.backend.model.Post;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
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
    Post resource = new Post();
    resource.add(linkTo(methodOn(PostController.class).getPostById(entity.getId())).withSelfRel());

    return resource
        .id(entity.getId())
        .contentType(Post.ContentTypeEnum.valueOf(entity.getContentType()))
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .author(AppUtil.extractAuthorInfo(entity.getAuthor()))
        .title(entity.getTitle())
        .text(entity.getText())
        .numVotes(entity.getNumVotes())
        .numReports(entity.getNumReports());
  }

  public List<Post> toListModel(Iterable<PostEntity> entities) {
    if (Objects.isNull(entities)) {
      return List.of();
    }
    return StreamSupport.stream(entities.spliterator(), false).map(this::toModel)
        .collect(toList());
  }
}
