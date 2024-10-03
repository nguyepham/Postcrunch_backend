package nguye.postcrunch.backend.security.auth;

import nguye.postcrunch.backend.model.AuthenticatedInfo;
import nguye.postcrunch.backend.model.RefreshToken;
import nguye.postcrunch.backend.model.User;
import nguye.postcrunch.backend.user.UserEntity;

import java.util.Optional;

public interface AuthService {

  UserEntity getUserByUsername(String username);

  AuthenticatedInfo createUser(User newUser);

  AuthenticatedInfo getAuthenticatedInfo(UserEntity entity);

  Optional<AuthenticatedInfo> getAccessToken(RefreshToken refreshToken);

  void removeRefreshToken(RefreshToken refreshToken);
}
