package nguye.postcrunch.backend.user;

import nguye.postcrunch.backend.api.UserApi;
import nguye.postcrunch.backend.exception.ResourceNotFoundException;
import nguye.postcrunch.backend.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Objects;

@RestController
public class UserController implements UserApi {

  private final UserRepresentationModelAssembler assembler;
  private final UserService service;

  public UserController(UserRepresentationModelAssembler assembler, UserService service) {
    this.assembler = assembler;
    this.service = service;
  }

  @Override
  public ResponseEntity<User> getUserById(String id) {
    return service.getUserById(id).map(assembler::toModel)
        .map(ResponseEntity::ok).orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public ResponseEntity<User> updateUser(User updatedUser) {

    if (Objects.isNull(updatedUser.getId())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    UserEntity entity = service.getUserById(updatedUser.getId()).orElseThrow(ResourceNotFoundException::new);

    if (Objects.nonNull(updatedUser.getUsername())) {
      entity.setUsername(updatedUser.getUsername());
    }

    if (Objects.nonNull(updatedUser.getPassword())) {
      entity.setPassword(updatedUser.getPassword());
    }

    if (Objects.nonNull(updatedUser.getFirstName())) {
      entity.setFirstName(updatedUser.getFirstName());
    }

    if (Objects.nonNull(updatedUser.getLastName())) {
      entity.setLastName(updatedUser.getLastName());
    }

    if (Objects.nonNull(updatedUser.getEmail())) {
      entity.setEmail(updatedUser.getEmail());
    }

    if (Objects.nonNull(updatedUser.getDob())) {
      // Assuming updatedUser.getDob() returns a Date or a Timestamp object
      entity.setDob(new Date(updatedUser.getDob().getTime()));
    }

    if (Objects.nonNull(updatedUser.getGender())) {
      entity.setGender(updatedUser.getGender());
    }

    return ResponseEntity.ok(assembler.toModel(service.updateUser(entity)));
  }

  @Override
  public ResponseEntity<Void> deleteUserById(String id) {
    if (service.getUserById(id).isPresent()) {
      service.deleteUserById(id);
      return ResponseEntity.noContent().build();
    }
    throw new ResourceNotFoundException();
  }
}
