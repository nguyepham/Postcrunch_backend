package nguye.postcrunch.backend.user;

import nguye.postcrunch.backend.api.UserApi;
import nguye.postcrunch.backend.model.User;
import nguye.postcrunch.backend.security.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class UserController implements UserApi {

  private final UserRepresentationModelAssembler assembler;
  private final UserService service;
  private final SecurityUtil securityUtil;

  public UserController(UserRepresentationModelAssembler assembler, UserService service, SecurityUtil securityUtil) {
    this.assembler = assembler;
    this.service = service;
    this.securityUtil = securityUtil;
  }

  @Override
  public ResponseEntity<User> getUserById(String id) {

    if (securityUtil.isSenderUser(id)) {
      return ResponseEntity.ok(assembler.toAuthorizedModel(service.getUserById(id)));
    }
    return ResponseEntity.ok(assembler.toModel(service.getUserById(id)));
  }

  @Override
  public ResponseEntity<User> updateUser(User updatedUser) {

    if (Objects.isNull(updatedUser.getId())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (!securityUtil.isSenderUser(updatedUser.getId())) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    return ResponseEntity.ok(assembler.toAuthorizedModel(service.updateUser(updatedUser)));
  }

  @Override
  public ResponseEntity<Void> deleteUserById(String id) {

    if (!securityUtil.isSenderUser(id)) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    service.deleteUserById(id);
    return ResponseEntity.accepted().build();
  }
}
