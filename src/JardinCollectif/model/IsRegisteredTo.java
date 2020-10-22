package JardinCollectif.model;

import JardinCollectif.annotations.Column;
import JardinCollectif.annotations.Entity;

@Entity
public class IsRegisteredTo {
  @Column(primaryKey = true)
  public int memberId;

  @Column(primaryKey = true)
  public String lotName;

  @Column
  public boolean requestStatus;

  public IsRegisteredTo(int memberId, String lotName, boolean requestStatus) {
    this.memberId = memberId;
    this.lotName = lotName;
    this.requestStatus = requestStatus;
  }

}