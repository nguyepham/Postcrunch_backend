package nguye.postcrunch.backend.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<UserEntity> getUserById(String id) {
    return repository.findById(id);
  }
}
