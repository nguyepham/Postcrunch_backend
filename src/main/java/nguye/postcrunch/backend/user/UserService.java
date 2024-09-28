package nguye.postcrunch.backend.user;

import java.util.Optional;

public interface UserService {

  Optional<UserEntity> getUserById(String id);

  UserEntity updateUser(UserEntity entity);

  void deleteUserById(String id);
}
