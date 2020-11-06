package JardinCollectif.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Lot {
  @Id
  public String lotName;

  public int maxMemberCount;

  @ManyToMany
  public List<Member> registrations;

  @ManyToMany
  public List<Member> pendingRegistrations;

  public Lot(String lotName, int maxMembercount) {
    this.lotName = lotName;
    this.maxMemberCount = maxMembercount;

    this.registrations = new LinkedList<>();
    this.pendingRegistrations = new LinkedList<>();
  }
}