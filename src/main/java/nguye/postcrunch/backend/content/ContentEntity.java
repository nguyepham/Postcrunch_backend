package nguye.postcrunch.backend.content;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nguye.postcrunch.backend.comment.CommentEntity;
import nguye.postcrunch.backend.user.UserEntity;
import nguye.postcrunch.backend.vote.VoteEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "CONTENT_TYPE", discriminatorType = DiscriminatorType.STRING)
@Table(name = "content", schema = "postcrunch")
@Getter
public abstract class ContentEntity {

  @Id
  @Column(name = "ID", nullable = false, updatable = false)
  private final String id = UUID.randomUUID().toString();

  @Column(name = "CONTENT_TYPE", insertable = false, updatable = false, nullable = false, length = 10)
  private String contentType;

  @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
  private final Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

  @Setter
  @Column(name = "UPDATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT NOW() ON UPDATE NOW()")
  private Timestamp updatedAt = createdAt;

  @Setter
  @Column(name = "TEXT", length = 3000)
  private String text;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
  private UserEntity author;

  @OneToMany(mappedBy = "target", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private final List<CommentEntity> comments = new ArrayList<>();

  @OneToMany(mappedBy = "target", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private final List<VoteEntity> votes = new ArrayList<>();

  // Public no-arg constructor
  public ContentEntity() {
  }

  // Constructor with non-nullable fields
  public ContentEntity(String contentType) {
    this.contentType = contentType;
  }
}
