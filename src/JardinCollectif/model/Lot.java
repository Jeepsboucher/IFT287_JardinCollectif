package JardinCollectif.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Lot {
  @Id  
  public String lotName;

  public int maxMemberCount;

  public Lot(String lotName, int maxMembercount) {
    this.lotName = lotName;
    this.maxMemberCount = maxMembercount;
  }
}