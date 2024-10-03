package nguye.postcrunch.backend.security.auth;

import nguye.postcrunch.backend.api.AuthApi;
import nguye.postcrunch.backend.exception.BadCredentialException;
import nguye.postcrunch.backend.exception.InvalidRefreshTokenException;
import nguye.postcrunch.backend.model.AuthenticatedInfo;
import nguye.postcrunch.backend.model.RefreshToken;
import nguye.postcrunch.backend.model.SignInReq;
import nguye.postcrunch.backend.model.User;
import nguye.postcrunch.backend.user.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

  private final PasswordEncoder passwordEncoder;
  private final AuthService service;

  public AuthController(PasswordEncoder passwordEncoder, AuthService service) {
    this.passwordEncoder = passwordEncoder;
    this.service = service;
  }

  @Override
  public ResponseEntity<AuthenticatedInfo> getAccessToken(RefreshToken refreshToken) {
    return ResponseEntity.ok(service.getAccessToken(refreshToken).orElseThrow(
        InvalidRefreshTokenException::new
    ));
  }

  @Override
  public ResponseEntity<AuthenticatedInfo> signUp(User user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(user));
  }

  @Override
  public ResponseEntity<AuthenticatedInfo> signIn(SignInReq signInReq) {
    UserEntity entity = service.getUserByUsername(signInReq.getUsername());
    if (passwordEncoder.matches(signInReq.getPassword(), entity.getPassword())) {
      return ResponseEntity.ok(service.getAuthenticatedInfo(entity));
    }
    throw new BadCredentialException("Invalid password or username.");
  }

  @Override
  public ResponseEntity<Void> signOut(RefreshToken refreshToken) {
    service.removeRefreshToken(refreshToken);
    return ResponseEntity.accepted().build();
  }
}
