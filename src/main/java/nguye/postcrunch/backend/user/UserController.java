package nguye.postcrunch.backend.user;

import nguye.postcrunch.backend.api.UserApi;
import nguye.postcrunch.backend.exception.ResourceNotFoundException;
import nguye.postcrunch.backend.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
    UserEntity updatedEntity = service.updateUser(updatedUser);
    return ResponseEntity.ok(assembler.toModel(updatedEntity));
  }

  @Override
  public ResponseEntity<Void> deleteUserById(String id) {
    service.deleteUserById(id);
    return ResponseEntity.noContent().build();
  }
}
