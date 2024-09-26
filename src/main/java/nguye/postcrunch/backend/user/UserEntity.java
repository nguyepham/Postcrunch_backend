package nguye.postcrunch.backend.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nguye.postcrunch.backend.post.ContentEntity;
import nguye.postcrunch.backend.report.ReportEntity;
import nguye.postcrunch.backend.vote.VoteEntity;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user", schema = "postcrunch")
@Getter
public class UserEntity {

  @Id
  @Column(name = "ID", nullable = false, updatable = false)
  private final String id = UUID.randomUUID().toString();

  @Column(name = "USERNAME", nullable = false, unique = true, length = 16)
  private String username;

  @Setter
  @Column(name = "PASSWORD", nullable = false, length = 40)
  private String password;

  @Setter
  @Column(name = "NAME", length = 28)
  private String name;

  @Setter
  @Column(name = "EMAIL", length = 24)
  private String email;

  @Setter
  @Column(name = "DOB")
  private java.sql.Date dob;

  @Setter
  @Column(name = "GENDER", length = 1)
  private String gender;

  @Setter
  @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
  private List<ContentEntity> contents;

  @Setter
  @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
  private List<VoteEntity> votes;

  @Setter
  @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
  private List<ReportEntity> reports;

  // Public no-arg constructor
  public UserEntity() {}

  // Constructor with non-nullable fields
  public UserEntity(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
