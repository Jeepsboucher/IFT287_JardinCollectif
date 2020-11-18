package JardinCollectif.model;

import org.bson.Document;

import JardinCollectif.annotations.Column;
import JardinCollectif.annotations.Entity;
import JardinCollectif.annotations.Initializer;

@Entity
public class Plant {
  @Column(primaryKey = true)
  public String plantName;
  @Column
  public int cultivationTime;

  @Initializer
  public Plant(Document d) {
    this.plantName = d.getString("plantName");
    this.cultivationTime = d.getInteger("cultivationTime");
  }

  public Plant(String plantName, int cultivationTime) {
    this.plantName = plantName;
    this.cultivationTime = cultivationTime;
  }
}