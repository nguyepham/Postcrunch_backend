package nguye.postcrunch.backend.user;

import nguye.postcrunch.backend.api.UserApi;
import nguye.postcrunch.backend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.notFound;

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
        .map(ResponseEntity::ok).orElse(notFound().build());
  }
}
