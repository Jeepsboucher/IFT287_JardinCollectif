package JardinCollectif.model;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.FIELD)
public class Member {
  @Id
  public long memberId;

  public boolean isAdmin;

  public String firstName;

  public String lastName;

  public String password;

  @ManyToMany(mappedBy = "registrations")
  public List<Lot> acceptedRegistrations;

  @OneToMany
  public List<Lot> pendingRegistrations;

  public Member(long memberId, boolean isAdmin, String firstName, String lastName, String password) {
    this.memberId = memberId;
    this.isAdmin = isAdmin;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
  }
}