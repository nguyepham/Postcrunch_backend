package nguye.postcrunch.backend.user;

import nguye.postcrunch.backend.model.User;

import java.util.Optional;

public interface UserService {

  UserEntity toEntity(User user);

  Optional<UserEntity> getUserById(String id);

  UserEntity updateUser(User updatedUser);

  void deleteUserById(String id);
}
