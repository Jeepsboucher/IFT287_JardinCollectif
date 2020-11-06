package JardinCollectif.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class IsSowedIn {
  @Id
  @GeneratedValue
  public long isSowedInId;

  public int quantity;

  public long memberId;

  public Date plantingDate;

  public String plantName;

  public String lotName;

  public IsSowedIn(long isSowedInId, int quantity, Date plantingDate, long memberId, String lotName, String plantName) {
    this.isSowedInId = isSowedInId;
    this.quantity = quantity;
    this.memberId = memberId;
    this.plantingDate = plantingDate;
    this.plantName = plantName;
    this.lotName = lotName;
  }
}