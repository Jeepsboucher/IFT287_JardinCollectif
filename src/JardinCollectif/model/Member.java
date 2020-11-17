package JardinCollectif.model;

import org.bson.Document;

import JardinCollectif.annotations.Column;
import JardinCollectif.annotations.Entity;
import JardinCollectif.annotations.Initializer;

@Entity
public class Member {
  @Column(primaryKey = true)
  public long memberId;
  @Column
  public boolean isAdmin;
  @Column
  public String firstName;
  @Column
  public String lastName;
  @Column
  public String password;

  @Initializer
  public Member(Document d) {
    this.memberId = d.getLong("memberId");
    this.isAdmin = d.getBoolean("isAdmin");
    this.firstName = d.getString("firstName");
    this.lastName = d.getString("lastName");
    this.password = d.getString("password");
  }

  public Member(long memberId, boolean isAdmin, String firstName, String lastName, String password) {
    this.memberId = memberId;
    this.isAdmin = isAdmin;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
  }

  public Document toDocument() {
    return new Document().append("memberId", memberId).append("isAdmin", isAdmin).append("firstName", firstName)
        .append("lastName", lastName).append("password", password);
  }
}