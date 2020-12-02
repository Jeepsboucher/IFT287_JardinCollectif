package JardinCollectif.model;

import JardinCollectif.annotations.Column;
import JardinCollectif.annotations.Entity;

@Entity
public class Lot {
  @Column(primaryKey = true)
  public String lotName;

  @Column
  public int maxMemberCount;

  public Lot(String lotName, int maxMembercount) {
    this.lotName = lotName;
    this.maxMemberCount = maxMembercount;
  }
}