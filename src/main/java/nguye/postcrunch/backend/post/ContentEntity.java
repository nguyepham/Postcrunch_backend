package nguye.postcrunch.backend.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nguye.postcrunch.backend.comment.CommentEntity;
import nguye.postcrunch.backend.report.ReportEntity;
import nguye.postcrunch.backend.user.UserEntity;
import nguye.postcrunch.backend.vote.VoteEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "content", schema = "postcrunch")
@Getter
public class ContentEntity {

  @Id
  @Column(name = "ID", nullable = false, updatable = false)
  private final String id = UUID.randomUUID().toString();

  @Column(name = "CONTENT_TYPE", nullable = false, length = 10)
  private String contentType;

  @Column(name = "NUM_REPORTS", columnDefinition = "INTEGER UNSIGNED", nullable = false)
  private int numReports = 0;

  @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
  private final LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "UPDATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT NOW() ON UPDATE NOW()")
  private LocalDateTime updatedAt = LocalDateTime.now();

  @Column(name = "TEXT", length = 3000)
  private String text;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
  private UserEntity author;

  @Setter
  @OneToMany(mappedBy = "target", fetch = FetchType.LAZY)
  private List<VoteEntity> votes;

  @Setter
  @OneToMany(mappedBy = "target", fetch = FetchType.LAZY)
  private List<ReportEntity> reports;

  @Setter
  @Transient
  private List<CommentEntity> comments;

  // Public no-arg constructor
  public ContentEntity() {}

  // Constructor with non-nullable fields
  public ContentEntity(String contentType) {
    this.contentType = contentType;
  }
}
