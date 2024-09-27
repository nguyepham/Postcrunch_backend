package nguye.postcrunch.backend.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nguye.postcrunch.backend.newsfeed.ContentEntity;

@Entity
@DiscriminatorValue("POST")
@Table(name = "post", schema = "postcrunch")
@Getter
public class PostEntity extends ContentEntity {

  @Setter
  @Column(name = "TITLE", length = 150)
  private String title;

  // Public no-arg constructor
  public PostEntity() {
    super("POST");
  }
}
