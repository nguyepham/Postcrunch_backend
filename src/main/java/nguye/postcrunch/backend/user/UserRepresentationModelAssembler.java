package nguye.postcrunch.backend.user;

import nguye.postcrunch.backend.model.User;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserRepresentationModelAssembler extends
    RepresentationModelAssemblerSupport<UserEntity, User> {

  public UserRepresentationModelAssembler() {
    super(UserController.class, User.class);
  }

  public User toAuthorizedModel(UserEntity entity) {

    return toModel(entity)
        .add(linkTo(methodOn(UserController.class).updateUser(null)).withRel("update"))
        .add(linkTo(methodOn(UserController.class).deleteUserById(entity.getId())).withRel("delete"));
  }

  @Override
  public User toModel(UserEntity entity) {
    User resource = new User();

    return resource
        .add(linkTo(methodOn(UserController.class).getUserById(entity.getId())).withSelfRel())
        .id(entity.getId())
        .username(entity.getUsername())
        .password(entity.getPassword())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .email(entity.getEmail())
        .dob(entity.getDob())
        .gender(entity.getGender());
  }
}
