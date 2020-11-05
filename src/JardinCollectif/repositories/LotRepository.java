package JardinCollectif.repositories;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.Lot;

public class LotRepository extends Repository<Lot> {
  public LotRepository(Connexion connexion) throws ClassNotFoundException, IFT287Exception {
    super(connexion);
  }
}