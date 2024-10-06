package nguye.postcrunch.backend.security.auth;

import nguye.postcrunch.backend.exception.GenericAlreadyExistsException;
import nguye.postcrunch.backend.exception.InvalidRefreshTokenException;
import nguye.postcrunch.backend.model.AuthPrincipal;
import nguye.postcrunch.backend.model.AuthenticatedInfo;
import nguye.postcrunch.backend.model.RefreshToken;
import nguye.postcrunch.backend.model.User;
import nguye.postcrunch.backend.security.JwtService;
import nguye.postcrunch.backend.security.UserTokenEntity;
import nguye.postcrunch.backend.security.UserTokenRepository;
import nguye.postcrunch.backend.user.UserEntity;
import nguye.postcrunch.backend.user.UserRepository;
import nguye.postcrunch.backend.user.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final UserService userService;
  private final JwtService jwtService;
  private final UserTokenRepository userTokenRepository;

  public AuthServiceImpl(UserRepository userRepository, UserService userService, JwtService jwtService, UserTokenRepository userTokenRepository) {
    this.userRepository = userRepository;
    this.userService = userService;
    this.jwtService = jwtService;
    this.userTokenRepository = userTokenRepository;
  }

  private AuthenticatedInfo createAuthenticatedInfo(UserEntity entity) {

    AuthPrincipal principal = new AuthPrincipal(entity.getUsername(), entity.getPassword());
    String jwt = jwtService.generateToken(principal);

    return new AuthenticatedInfo()
        .username(entity.getUsername())
        .accessToken(jwt)
        .userId(entity.getId());
  }

  private static class RandomHolder {
    static final Random random = new SecureRandom();

    public static String randomKey(int length) {
      return String.format(
              "%" + length + "s", new BigInteger(length * 5 /*base 32,2^5*/, random).toString(32))
          .replace(' ', '0');
    }
  }

  private String createRefreshToken(UserEntity user) {
    String token = RandomHolder.randomKey(128);
    userTokenRepository.save(new UserTokenEntity().setRefreshToken(token).setUser(user));
    return token;
  }


  private AuthenticatedInfo createAuthenticatedInfoWithRefreshToken(UserEntity entity) {
    return createAuthenticatedInfo(entity).refreshToken(createRefreshToken(entity));
  }

  @Override
  public UserEntity getUserByUsername(String username) {
    if (Strings.isBlank(username)) {
      throw new UsernameNotFoundException("Invalid username.");
    }
    final String name = username.trim();
    return userRepository.findByUsername(name).orElseThrow(
        () -> new UsernameNotFoundException("The requested user not found.")
    );
  }

  @Override
  public AuthenticatedInfo createUser(User newUser) {

    Integer count = userRepository.findByUsernameOrEmail(newUser.getUsername(), newUser.getEmail());
    if (count > 0) {
      throw new GenericAlreadyExistsException("Use different username or email");
    }
    UserEntity userEntity = userRepository.save(userService.toEntity(newUser));

    return createAuthenticatedInfoWithRefreshToken(userEntity);
  }

  @Override
  @Transactional
  public AuthenticatedInfo getAuthenticatedInfo(UserEntity entity) {
    userTokenRepository.deleteByUserId(entity.getId());
    return createAuthenticatedInfoWithRefreshToken(entity);
  }

  @Override
  public Optional<AuthenticatedInfo> getAccessToken(RefreshToken refreshToken) {
    return userTokenRepository.findByRefreshToken(refreshToken.getValue())
        .map(userTokenEntity ->
            Optional.of(createAuthenticatedInfo(userTokenEntity.getUser())
                .refreshToken(refreshToken.getValue())))
        .orElseThrow(() -> new InvalidRefreshTokenException("Invalid token."));
  }

  @Override
  public void removeRefreshToken(RefreshToken refreshToken) {
    userTokenRepository
        .findByRefreshToken(refreshToken.getValue())
        .ifPresentOrElse(
            userTokenRepository::delete,
            () -> {
              throw new InvalidRefreshTokenException("Invalid token.");
            });
  }
}
