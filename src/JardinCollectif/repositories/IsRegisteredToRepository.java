package JardinCollectif.repositories;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.IsRegisteredTo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IsRegisteredToRepository extends Repository<IsRegisteredTo> {
  private final PreparedStatement countMemberRegisteredToALotStatement;
  private final PreparedStatement countMembershipInLotStatement;
  private final PreparedStatement deleteRequestToJoinLotStatement;

  public IsRegisteredToRepository(Connexion connexion) throws ClassNotFoundException, SQLException, IFT287Exception {
    super(connexion);

    countMemberRegisteredToALotStatement = connexion.getConnection()
            .prepareStatement("SELECT COUNT(memberId) FROM RequestToJoin WHERE memberId = ?;");
    countMembershipInLotStatement = connexion.getConnection()
            .prepareStatement("SELECT COUNT(lotName) FROM RequestToJoin WHERE lotName = ?;");
    deleteRequestToJoinLotStatement = connexion.getConnection()
            .prepareStatement("DELETE FROM RequestToJoin WHERE lotName = ?");
  }

  public boolean isMemberRegisteredToALot(int memberId) throws SQLException {
    countMemberRegisteredToALotStatement.setInt(1, memberId);
    ResultSet result = countMemberRegisteredToALotStatement.executeQuery();
    result.next();
    return result.getInt(1) > 0;
  }

  public int countMembershipInLot(String lotName) throws SQLException {
    countMembershipInLotStatement.setString(1, lotName);
    ResultSet result = countMembershipInLotStatement.executeQuery();
    result.next();
    return result.getInt(1);
  }

  public int deleteRequestToJoinLot(String lotName) throws SQLException {
    deleteRequestToJoinLotStatement.setString(1, lotName);
    return deleteRequestToJoinLotStatement.executeUpdate();
  }
}