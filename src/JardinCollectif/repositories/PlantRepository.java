package JardinCollectif.repositories;

import JardinCollectif.Connection;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.Plant;

public class PlantRepository extends Repository<Plant> {
  public PlantRepository(Connection connection) throws ClassNotFoundException, IFT287Exception {
    super(connection);
  }
}