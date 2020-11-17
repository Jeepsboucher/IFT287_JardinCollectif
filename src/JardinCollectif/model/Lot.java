package JardinCollectif.model;

import org.bson.Document;

import JardinCollectif.annotations.Column;
import JardinCollectif.annotations.Entity;
import JardinCollectif.annotations.Initializer;

@Entity
public class Lot {
  @Column(primaryKey = true)
  public String lotName;
  @Column
  public int maxMemberCount;

  @Initializer
  public Lot(Document d) {
    this.lotName = d.getString("lotName");
    this.maxMemberCount = d.getInteger("maxMemberCount");
  }

  public Lot(String lotName, int maxMembercount) {
    this.lotName = lotName;
    this.maxMemberCount = maxMembercount;
  }

  public Document toDocument() {
    return new Document().append("lotName", lotName).append("maxMemberCount", maxMemberCount);
  }
}