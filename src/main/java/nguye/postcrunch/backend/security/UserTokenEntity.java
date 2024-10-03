package nguye.postcrunch.backend.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import nguye.postcrunch.backend.user.UserEntity;

import java.util.UUID;

@Entity
@Table(name = "user_token")
@Getter
public class UserTokenEntity {

  @Id
  @Column(name = "ID", updatable = false, nullable = false)
  private final String id = UUID.randomUUID().toString();

  @NotNull(message = "Refresh token is required.")
  @Basic(optional = false)
  @Column(name = "refresh_token")
  private String refreshToken;

  @ManyToOne(fetch = FetchType.LAZY)
  private UserEntity user;

  public UserTokenEntity setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

  public UserTokenEntity setUser(UserEntity user) {
    this.user = user;
    return this;
  }
}
