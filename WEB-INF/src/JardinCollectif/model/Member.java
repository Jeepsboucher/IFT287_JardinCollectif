package JardinCollectif.model;

import JardinCollectif.annotations.Column;
import JardinCollectif.annotations.Entity;

@Entity
public class Member {
  @Column(primaryKey = true)
  public int memberId;

  @Column
  public boolean isAdmin;

  @Column
  public String firstName;

  @Column
  public String lastName;

  @Column
  public String password;

  public Member(int memberId, boolean isAdmin, String firstName, String lastName, String password) {
    this.memberId = memberId;
    this.isAdmin = isAdmin;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
  }
}