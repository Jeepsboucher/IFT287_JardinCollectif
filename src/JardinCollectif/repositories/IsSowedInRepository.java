package JardinCollectif.repositories;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.IsSowedIn;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.TypedQuery;

public class IsSowedInRepository extends Repository<IsSowedIn> {
  private final TypedQuery<IsSowedIn> retrieveFromPlantStatement;
  private final TypedQuery<IsSowedIn> retrieveFromLotStatement;
  private final TypedQuery<Long> deletePlantsOlderThanWithNameInLotStatement;
  private final TypedQuery<Long> quantitySowStatement;

  public IsSowedInRepository(Connexion connexion) throws ClassNotFoundException, SQLException, IFT287Exception {
    super(connexion);
    retrieveFromPlantStatement = connexion.getEntityManager()
        .createQuery("SELECT i FROM IsSowedIn i WHERE plantName = ?1;", IsSowedIn.class);
    retrieveFromLotStatement = connexion.getEntityManager().createQuery("SELECT i FROM IsSowedIn i WHERE lotName = ?1",
        IsSowedIn.class);
    deletePlantsOlderThanWithNameInLotStatement = connexion.getEntityManager()
        .createQuery("DELETE FROM IsSowedIn i WHERE i.plantingDate <= ?1 AND i.plantName = ?2 AND i.lotName = ?3", Long.class);
    quantitySowStatement = connexion.getEntityManager()
        .createQuery("SELECT SUM(i.quantity) FROM IsSowedIn i WHERE plantName = ?1", Long.class);
  }

  public List<IsSowedIn> retrieveFromPlant(String plantName) throws IFT287Exception, SQLException {
    retrieveFromPlantStatement.setParameter(1, plantName);
    List<IsSowedIn> isSowedInResults = retrieveFromPlantStatement.getResultList();
    return isSowedInResults;
  }

  public List<IsSowedIn> retrieveFromLot(String lotName) throws SQLException, IFT287Exception {
    retrieveFromLotStatement.setParameter(1, lotName);
    List<IsSowedIn> isSowedInResults = retrieveFromLotStatement.getResultList();
    return isSowedInResults;
  }

  public int deletePlantsOlderThanWithNameInLot(Date plantingDate, String plantName, String lotName)
      throws SQLException, IFT287Exception {
    deletePlantsOlderThanWithNameInLotStatement.setParameter(1, plantingDate);
    deletePlantsOlderThanWithNameInLotStatement.setParameter(2, plantName);
    deletePlantsOlderThanWithNameInLotStatement.setParameter(3, lotName);

    return deletePlantsOlderThanWithNameInLotStatement.executeUpdate();
  }

  public int getQuantitySowed(String plantName) throws SQLException, IFT287Exception {
    quantitySowStatement.setParameter(1, plantName);
    Long result = quantitySowStatement.getSingleResult();
    return result.intValue();
  }
}