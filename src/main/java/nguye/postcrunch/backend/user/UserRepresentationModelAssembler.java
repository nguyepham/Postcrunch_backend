package nguye.postcrunch.backend.user;

import nguye.postcrunch.backend.model.User;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

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
