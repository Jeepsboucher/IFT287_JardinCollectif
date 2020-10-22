package JardinCollectif.repositories;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.IsRegisteredTo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IsRegisteredToRepository extends Repository<IsRegisteredTo> {
  private PreparedStatement isMemberRegisteredToALotStatement;
  public IsRegisteredToRepository(Connexion connexion) throws ClassNotFoundException, SQLException, IFT287Exception {
    super(connexion);

    isMemberRegisteredToALotStatement = connexion.getConnection()
            .prepareStatement("SELECT COUNT(*) FROM RequestToJoin WHERE memberId = ?;");
  }
  
  public boolean isMemberRegisteredToALot(int memberId) throws SQLException {
    isMemberRegisteredToALotStatement.setInt(1, memberId);
    ResultSet result = isMemberRegisteredToALotStatement.executeQuery();
    result.next();
    return result.getInt(1) > 0;
  }
}