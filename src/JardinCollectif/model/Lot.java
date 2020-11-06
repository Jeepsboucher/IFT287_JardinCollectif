package JardinCollectif.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Lot {
  @Id  
  public String lotName;

  public int maxMemberCount;

  @ManyToMany(mappedBy = "acceptedRegistrations")
  public List<Member> registrations;

  public Lot(String lotName, int maxMembercount) {
    this.lotName = lotName;
    this.maxMemberCount = maxMembercount;
  }
}