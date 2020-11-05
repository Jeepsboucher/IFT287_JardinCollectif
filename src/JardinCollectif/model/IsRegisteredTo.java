package JardinCollectif.model;

import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class IsRegisteredTo {
  @Id
  public long memberId;

  @Id
  public String lotName;

  public boolean requestStatus;

  public IsRegisteredTo(long memberId, String lotName, boolean requestStatus) {
    this.memberId = memberId;
    this.lotName = lotName;
    this.requestStatus = requestStatus;
  }
}