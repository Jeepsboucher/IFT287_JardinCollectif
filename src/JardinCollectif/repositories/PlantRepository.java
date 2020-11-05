package JardinCollectif.repositories;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.Plant;

public class PlantRepository extends Repository<Plant> {
  public PlantRepository(Connexion connexion) throws ClassNotFoundException, IFT287Exception {
    super(connexion);
  }
}