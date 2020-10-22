package JardinCollectif.repositories;

import java.sql.SQLException;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.Plant;

public class PlantRepository extends Repository<Plant> {
  public PlantRepository(Connexion connexion) throws ClassNotFoundException, SQLException, IFT287Exception {
    super(connexion);
  }
}