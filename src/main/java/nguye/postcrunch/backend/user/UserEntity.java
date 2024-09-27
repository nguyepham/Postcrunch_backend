package nguye.postcrunch.backend.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nguye.postcrunch.backend.newsfeed.ContentEntity;
import nguye.postcrunch.backend.report.ReportEntity;
import nguye.postcrunch.backend.vote.VoteEntity;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user", schema = "postcrunch")
@Getter
@Setter
public class UserEntity {

  @Id
  @Column(name = "ID", nullable = false, updatable = false)
  private final String id = UUID.randomUUID().toString();

  @Column(name = "USERNAME", nullable = false, unique = true, length = 16)
  private String username;

  @Column(name = "PASSWORD", nullable = false, length = 40)
  private String password;

  @Column(name = "FIRST_NAME", length = 28)
  private String firstName;

  @Column(name = "LAST_NAME", length = 28)
  private String lastName;

  @Column(name = "EMAIL", length = 24)
  private String email;

  @Column(name = "DOB")
  private java.sql.Date dob;

  @Column(name = "GENDER", length = 1)
  private String gender;

  @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
  private List<ContentEntity> contents;

  @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
  private List<VoteEntity> votes;

  @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
  private List<ReportEntity> reports;

  // Public no-arg constructor
  public UserEntity() {
  }

  // Constructor with non-nullable fields
  public UserEntity(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
