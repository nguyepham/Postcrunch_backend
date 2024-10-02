package nguye.postcrunch.backend.user;

import nguye.postcrunch.backend.model.User;

public interface UserService {

  UserEntity toEntity(User user);

  UserEntity getUserById(String id);

  UserEntity updateUser(User updatedUser);

  void deleteUserById(String id);
}
