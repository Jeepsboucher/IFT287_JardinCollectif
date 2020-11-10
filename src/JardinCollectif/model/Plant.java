package JardinCollectif.model;

import org.bson.Document;

public class Plant {
  public String plantName;

  public int cultivationTime;

  public Plant(Document d) {
    this.plantName = d.getString("plantName");
    this.cultivationTime = d.getInteger("cultivationTime");
  }

  public Plant(String plantName, int cultivationTime) {
    this.plantName = plantName;
    this.cultivationTime = cultivationTime;
  }

  public Document toDocument() {
    return new Document().append("plantName", plantName).append("cultivationTime", cultivationTime);
  }
}