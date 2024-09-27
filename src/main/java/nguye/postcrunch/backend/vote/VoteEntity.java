package nguye.postcrunch.backend.vote;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nguye.postcrunch.backend.newsfeed.ContentEntity;
import nguye.postcrunch.backend.user.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vote", schema = "postcrunch")
@Getter
public class VoteEntity {

  @Id
  @Column(name = "ID", nullable = false, updatable = false)
  private final String id = UUID.randomUUID().toString();

  @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
  private final LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "VOTE_TYPE", nullable = false, length = 10)
  private String voteType;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
  private UserEntity author;

  @ManyToOne
  @JoinColumn(name = "TARGET_ID", referencedColumnName = "ID", nullable = false)
  private ContentEntity target;

  // Public no-arg constructor
  public VoteEntity() {
  }

  // Constructor with non-nullable fields
  public VoteEntity(String voteType, ContentEntity target) {
    this.voteType = voteType;
    this.target = target;
  }
}
