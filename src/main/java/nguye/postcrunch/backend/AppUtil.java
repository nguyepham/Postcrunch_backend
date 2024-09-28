package nguye.postcrunch.backend;

import nguye.postcrunch.backend.model.AuthorInfo;
import nguye.postcrunch.backend.model.Post;
import nguye.postcrunch.backend.model.PostPreview;
import nguye.postcrunch.backend.post.PostController;
import nguye.postcrunch.backend.user.UserController;
import nguye.postcrunch.backend.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppUtil {

  public static AuthorInfo extractAuthorInfo(UserEntity entity) {
    AuthorInfo info = new AuthorInfo();

    info.add(linkTo(methodOn(UserController.class).getUserById(entity.getId())).withSelfRel());

    return info
        .id(entity.getId())
        .username(entity.getUsername())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName());
  }

  public static List<PostPreview> extractPreview(List<Post> posts) {
    List<PostPreview> previews = new ArrayList<>();

    posts.forEach(post -> {
      PostPreview preview = new PostPreview()
          .add(linkTo(methodOn(PostController.class).getPostById(post.getId())).withSelfRel())
          .id(post.getId())
          .createdAt(post.getCreatedAt())
          .updatedAt(post.getUpdatedAt())
          .author(post.getAuthor())
          .title(post.getTitle());
      previews.add(preview);
    });

    return previews;
  }
}
