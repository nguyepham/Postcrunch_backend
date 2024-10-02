package nguye.postcrunch.backend.user;

import nguye.postcrunch.backend.exception.ResourceNotFoundException;
import nguye.postcrunch.backend.model.User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserEntity toEntity(User user) {
    UserEntity entity = new UserEntity(user.getUsername(), user.getPassword());

    entity.setFirstName(user.getFirstName());
    entity.setLastName(user.getLastName());
    entity.setEmail(user.getEmail());
    entity.setDob(new java.sql.Date(user.getDob().getTime()));
    entity.setGender(user.getGender());

    return entity;
  }

  @Override
  public UserEntity getUserById(String id) {
    return repository.findById(id).orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public UserEntity updateUser(User updatedUser) {

    UserEntity entity = getUserById(updatedUser.getId());

    if (Objects.nonNull(updatedUser.getUsername())) {
      entity.setUsername(updatedUser.getUsername());
    }

    if (Objects.nonNull(updatedUser.getPassword())) {
      entity.setPassword(updatedUser.getPassword());
    }

    if (Objects.nonNull(updatedUser.getFirstName())) {
      entity.setFirstName(updatedUser.getFirstName());
    }

    if (Objects.nonNull(updatedUser.getLastName())) {
      entity.setLastName(updatedUser.getLastName());
    }

    if (Objects.nonNull(updatedUser.getEmail())) {
      entity.setEmail(updatedUser.getEmail());
    }

    if (Objects.nonNull(updatedUser.getDob())) {
      // Assuming updatedUser.getDob() returns a Date or a Timestamp object
      entity.setDob(new java.sql.Date(updatedUser.getDob().getTime()));
    }

    if (Objects.nonNull(updatedUser.getGender())) {
      entity.setGender(updatedUser.getGender());
    }
    return repository.save(entity);
  }

  @Override
  public void deleteUserById(String id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    }
  }
}
