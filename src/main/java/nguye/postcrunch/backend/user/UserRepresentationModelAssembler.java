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

  @Override
  public User toModel(UserEntity entity) {
    User resource = new User();

    resource.add(linkTo(methodOn(UserController.class).getUserById(entity.getId())).withSelfRel());
    resource.add(linkTo(methodOn(UserController.class).updateUser(null)).withRel("update"));
    resource.add(linkTo(methodOn(UserController.class).deleteUserById(entity.getId())).withRel("delete"));

    return resource
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
