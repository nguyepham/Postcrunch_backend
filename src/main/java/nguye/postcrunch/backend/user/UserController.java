package nguye.postcrunch.backend.user;

import nguye.postcrunch.backend.api.UserApi;
import nguye.postcrunch.backend.exception.ResourceNotFoundException;
import nguye.postcrunch.backend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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
  public ResponseEntity<User> updateUser(@RequestBody User updateUser) {
    UserEntity entity = service.getUserById(updateUser.getId()).orElseThrow(ResourceNotFoundException::new);

    if (Objects.nonNull(updateUser.getUsername())) {
      entity.setUsername(updateUser.getUsername());
    }

    if (Objects.nonNull(updateUser.getPassword())) {
      entity.setPassword(updateUser.getPassword());
    }

    if (Objects.nonNull(updateUser.getFirstName())) {
      entity.setFirstName(updateUser.getFirstName());
    }

    if (Objects.nonNull(updateUser.getLastName())) {
      entity.setLastName(updateUser.getLastName());
    }

    if (Objects.nonNull(updateUser.getEmail())) {
      entity.setEmail(updateUser.getEmail());
    }

    if (Objects.nonNull(updateUser.getDob())) {
      // Assuming updateUser.getDob() returns a Date or a Timestamp object
      entity.setDob(new Date(updateUser.getDob().getTime()));
    }

    if (Objects.nonNull(updateUser.getGender())) {
      entity.setGender(updateUser.getGender());
    }

    UserEntity savedUser = service.updateUser(entity);
    return ResponseEntity.ok(assembler.toModel(savedUser));
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
