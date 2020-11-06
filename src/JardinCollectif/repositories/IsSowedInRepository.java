package JardinCollectif.repositories;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.IsSowedIn;

import javax.persistence.TypedQuery;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class IsSowedInRepository extends Repository<IsSowedIn> {
  private final TypedQuery<IsSowedIn> retrieveFromPlantQuery;
  private final TypedQuery<IsSowedIn> retrieveFromLotQuery;
  private final TypedQuery<Long> deletePlantsOlderThanWithNameInLotQuery;
  private final TypedQuery<Integer> quantitySowQuery;

  public IsSowedInRepository(Connexion connexion) throws ClassNotFoundException, SQLException, IFT287Exception {
    super(connexion);
    retrieveFromPlantQuery = connexion.getEntityManager()
            .createQuery("SELECT i FROM IsSowedIn i WHERE i.plantName = ?1", IsSowedIn.class);
    retrieveFromLotQuery = connexion.getEntityManager().createQuery("SELECT i FROM IsSowedIn i WHERE i.lotName = ?1",
            IsSowedIn.class);
    deletePlantsOlderThanWithNameInLotQuery = connexion.getEntityManager().createQuery(
            "DELETE FROM IsSowedIn i WHERE i.plantingDate <= ?1 AND i.plantName = ?2 AND i.lotName = ?3", Long.class);
    quantitySowQuery = connexion.getEntityManager()
            .createQuery("SELECT SUM(i.quantity) FROM IsSowedIn i WHERE i.plantName = ?1", Integer.class);
  }

  public List<IsSowedIn> retrieveFromPlant(String plantName) throws IFT287Exception, SQLException {
    retrieveFromPlantQuery.setParameter(1, plantName);
    List<IsSowedIn> isSowedInResults = retrieveFromPlantQuery.getResultList();
    return isSowedInResults;
  }

  public List<IsSowedIn> retrieveFromLot(String lotName) throws SQLException, IFT287Exception {
    retrieveFromLotQuery.setParameter(1, lotName);
    List<IsSowedIn> isSowedInResults = retrieveFromLotQuery.getResultList();
    return isSowedInResults;
  }

  public int deletePlantsOlderThanWithNameInLot(Date plantingDate, String plantName, String lotName)
          throws SQLException, IFT287Exception {
    deletePlantsOlderThanWithNameInLotQuery.setParameter(1, plantingDate);
    deletePlantsOlderThanWithNameInLotQuery.setParameter(2, plantName);
    deletePlantsOlderThanWithNameInLotQuery.setParameter(3, lotName);

    return deletePlantsOlderThanWithNameInLotQuery.executeUpdate();
  }

  public int getQuantitySowed(String plantName) throws SQLException, IFT287Exception {
    quantitySowQuery.setParameter(1, plantName);
    Integer result = quantitySowQuery.getSingleResult();
    return result == null ? 0 : result;
  }
}