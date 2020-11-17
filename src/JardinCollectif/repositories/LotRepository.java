package JardinCollectif.repositories;

import JardinCollectif.Connection;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.Lot;

public class LotRepository extends Repository<Lot> {
  public LotRepository(Connection connection) throws ClassNotFoundException, IFT287Exception {
    super(connection);
  }
}