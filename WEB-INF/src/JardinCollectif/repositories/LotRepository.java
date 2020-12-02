package JardinCollectif.repositories;

import java.sql.SQLException;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.Lot;

public class LotRepository extends Repository<Lot> {
  public LotRepository(Connexion connexion) throws ClassNotFoundException, SQLException, IFT287Exception {
    super(connexion);
  }
}