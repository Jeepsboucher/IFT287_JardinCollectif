package JardinCollectif.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.IsSowedIn;

public class IsSowedInRepository extends Repository<IsSowedIn> {
  private PreparedStatement retrieveFromPlantStatement;
  private PreparedStatement retrieveFromLotStatement;
  private PreparedStatement deletePlantsOlderThanWithNameInLotStatement;
  private PreparedStatement quantitySowStatement;

  public IsSowedInRepository(Connexion connexion) throws ClassNotFoundException, SQLException, IFT287Exception {
    super(connexion);
    retrieveFromPlantStatement = connexion.getConnection()
            .prepareStatement("SELECT * FROM IsSowedIn WHERE plantName = ?;");
    retrieveFromLotStatement = connexion.getConnection()
            .prepareStatement("SELECT * FROM IsSowedIn WHERE lotName = ?;");
    deletePlantsOlderThanWithNameInLotStatement = connexion.getConnection()
            .prepareStatement("DELETE FROM IsSowedIn WHERE plantingDate <= ? AND plantName = ? AND lotName = ?;");
    quantitySowStatement = connexion.getConnection()
            .prepareStatement("SELECT SUM(quantity) FROM IsSowedIn WHERE plantName = ?;");
  }

  public List<IsSowedIn> retrieveFromPlant(String plantName) throws IFT287Exception, SQLException {
    List<IsSowedIn> isSowedInResults = new ArrayList<>();
    retrieveFromPlantStatement.setString(1, plantName);
    
    ResultSet results = retrieveFromPlantStatement.executeQuery();
    while (results.next()) {
      isSowedInResults.add(instantiateEntity(results));
    }

    return isSowedInResults;
  }

  public List<IsSowedIn> retrieveFromLot(String lotName) throws SQLException, IFT287Exception {
    List<IsSowedIn> isSowedInResults = new ArrayList<>();
    retrieveFromLotStatement.setString(1, lotName);
    
    ResultSet results = retrieveFromLotStatement.executeQuery();
    while (results.next()) {
      isSowedInResults.add(instantiateEntity(results));
    }

    return isSowedInResults;
  }

  public int deletePlantsOlderThanWithNameInLot(Date plantingDate, String plantName, String lotName) throws SQLException, IFT287Exception {
    deletePlantsOlderThanWithNameInLotStatement.setDate(1, plantingDate);
    deletePlantsOlderThanWithNameInLotStatement.setString(2, plantName);
    deletePlantsOlderThanWithNameInLotStatement.setString(3, lotName);
  
    return deletePlantsOlderThanWithNameInLotStatement.executeUpdate();
  }

  public int getQuantitySowed(String plantName) throws SQLException, IFT287Exception {
    quantitySowStatement.setString(1, plantName);

    ResultSet result = quantitySowStatement.executeQuery();
    result.next();
    return result.getInt(1);
  }
}