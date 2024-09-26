package nguye.postcrunch.backend.comment;

import jakarta.persistence.*;
import lombok.Getter;
import nguye.postcrunch.backend.post.ContentEntity;

@Entity
@Table(name = "comment", schema = "postcrunch")
@Getter
public class CommentEntity extends ContentEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "TARGET_ID", referencedColumnName = "ID", nullable = false)
  private ContentEntity target;

  // Public no-arg constructor
  public CommentEntity() {}

  // Constructor with non-nullable fields
  public CommentEntity(ContentEntity target) {
    this.target = target;
  }
}
