package nguye.postcrunch.backend.util;

import nguye.postcrunch.backend.model.*;
import nguye.postcrunch.backend.user.UserController;
import nguye.postcrunch.backend.user.UserEntity;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthorInfoExtractor {

  public static AuthorInfo extractAuthorInfo(UserEntity entity) {
    AuthorInfo info = new AuthorInfo();

    info.add(linkTo(methodOn(UserController.class).getUserById(entity.getId())).withSelfRel());

    return info
        .id(entity.getId())
        .username(entity.getUsername())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName());
  }
}
