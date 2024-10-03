package nguye.postcrunch.backend.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository
    extends PagingAndSortingRepository<UserEntity, String>, CrudRepository<UserEntity, String> {

  Optional<UserEntity> findByUsername(String username);

  @Query("select count(user) from UserEntity user " +
        "where user.username = :username " +
        "or user.email = :email")
  Integer findByUsernameOrEmail(String username, String email);
}
