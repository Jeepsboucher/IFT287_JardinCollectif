package JardinCollectif.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Plant {
  @Id
  public String plantName;

  public int cultivationTime;

  public Plant(String plantName, int cultivationTime) {
    this.plantName = plantName;
    this.cultivationTime = cultivationTime;
  }
}