package nguye.postcrunch.backend.security;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserTokenRepository extends CrudRepository<UserTokenEntity, String> {

  Optional<UserTokenEntity> findByRefreshToken(String refreshToken);

  Optional<UserTokenEntity> deleteByUserId(String userId);
}
