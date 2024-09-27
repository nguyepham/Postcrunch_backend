package nguye.postcrunch.backend.report;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nguye.postcrunch.backend.newsfeed.ContentEntity;
import nguye.postcrunch.backend.user.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "report", schema = "postcrunch")
@Getter
public class ReportEntity {

  @Id
  @Column(name = "ID", nullable = false, updatable = false)
  private final String id = UUID.randomUUID().toString();

  @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
  private final LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "REASON", nullable = false, length = 16)
  private String reason;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
  private UserEntity author;

  @ManyToOne
  @JoinColumn(name = "TARGET_ID", referencedColumnName = "ID", nullable = false)
  private ContentEntity target;

  // Public no-arg constructor
  public ReportEntity() {
  }

  // Constructor with non-nullable fields
  public ReportEntity(String reason, ContentEntity target) {
    this.reason = reason;
    this.target = target;
  }
}
