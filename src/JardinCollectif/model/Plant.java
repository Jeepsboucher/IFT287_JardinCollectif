package JardinCollectif.model;

import JardinCollectif.annotations.Column;
import JardinCollectif.annotations.Entity;

@Entity
public class Plant {
  @Column(primaryKey = true)
  public String plantName;

  @Column
  public int cultivationTime;

  public Plant(String plantName, int cultivationTime) {
    this.plantName = plantName;
    this.cultivationTime = cultivationTime;
  }
}