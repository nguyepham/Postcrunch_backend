package nguye.postcrunch.backend.user;

import nguye.postcrunch.backend.model.User;
import nguye.postcrunch.backend.post.PostRepresentationModelAssembler;
import nguye.postcrunch.backend.post.PostService;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<UserEntity, User> {

  private final PostService postService;
  private final PostRepresentationModelAssembler postRepresentationModelAssembler;

  public UserRepresentationModelAssembler(PostService postService,
                                          PostRepresentationModelAssembler postRepresentationModelAssembler) {
    super(UserController.class, User.class);
    this.postService = postService;
    this.postRepresentationModelAssembler = postRepresentationModelAssembler;
  }

  @Override
  public User toModel(UserEntity entity) {
    User resource = new User();

    resource.add(linkTo(methodOn(UserController.class).getUserById(entity.getId())).withSelfRel());

    return resource
        .id(entity.getId())
        .username(entity.getUsername())
        .password(entity.getPassword())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .email(entity.getEmail())
        .dob(entity.getDob())
        .gender(entity.getGender())
        .posts(postRepresentationModelAssembler.toListModel(
            postService.getPostsByAuthorId(entity.getId()).orElse(List.of())
        ));
  }
}
