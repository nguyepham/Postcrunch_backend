package nguye.postcrunch.backend.security;

import nguye.postcrunch.backend.user.UserEntity;
import nguye.postcrunch.backend.user.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository repository;

  public UserDetailsServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<UserEntity> optUser = repository.findByUsername(username);
    User.UserBuilder builder;

    if (optUser.isPresent()) {
      UserEntity user = optUser.get();

      // Create the UserDetails object
      builder = User
          .withUsername(username)
          .password(user.getPassword());
    } else {
      throw new UsernameNotFoundException("User not found");
    }

    return builder.build();
  }
}
