package JardinCollectif.model;

import java.sql.Date;

import org.bson.Document;

public class IsSowedIn {

  public long isSowedInId;

  public int quantity;

  public long memberId;

  public Date plantingDate;

  public String plantName;

  public String lotName;

  public IsSowedIn(Document d) {
    this.isSowedInId = d.getInteger("isSowedInId");
    this.quantity = d.getInteger("quantity");
    this.memberId = d.getInteger("memberId");
    this.plantingDate = (Date) d.getDate("plantingDate");
    this.plantName = d.getString("plantName");
    this.lotName = d.getString("lotName");
  }

  public IsSowedIn(long isSowedInId, int quantity, Date plantingDate, long memberId, String lotName, String plantName) {
    this.isSowedInId = isSowedInId;
    this.quantity = quantity;
    this.memberId = memberId;
    this.plantingDate = plantingDate;
    this.plantName = plantName;
    this.lotName = lotName;
  }

  public Document toDocument() {
    return new Document().append("isSowedInId", isSowedInId).append("quantity", quantity).append("memberId", memberId)
        .append("plantingDate", plantingDate).append("plantName", plantName).append("lotName", lotName);
  }
}