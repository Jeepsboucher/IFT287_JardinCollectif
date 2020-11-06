package JardinCollectif.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Member {
  @Id
  public long memberId;

  public boolean isAdmin;

  public String firstName;

  public String lastName;

  public String password;

  @ManyToMany(mappedBy = "registrations")
  public List<Lot> acceptedRegistrations;

  @ManyToMany
  public List<Lot> pendingRegistrations;

  public Member(long memberId, boolean isAdmin, String firstName, String lastName, String password) {
    this.memberId = memberId;
    this.isAdmin = isAdmin;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;

    this.acceptedRegistrations = new LinkedList<>();
    this.pendingRegistrations = new LinkedList<>();
  }
}