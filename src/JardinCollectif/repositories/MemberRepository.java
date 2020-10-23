package JardinCollectif.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.Member;

public class MemberRepository extends Repository<Member> {
  private PreparedStatement retrieveMembersInLotStatement;

  public MemberRepository(Connexion connexion) throws ClassNotFoundException, SQLException, IFT287Exception {
    super(connexion);

    retrieveMembersInLotStatement = connexion.getConnection()
            .prepareStatement("SELECT Member.* FROM Member " +
                    "INNER JOIN RequestToJoin ON Member.memberId = RequestToJoin.memberId " +
                    "INNER JOIN Lot ON Lot.lotName = RequestToJoin.lotName " +
                    "WHERE RequestToJoin.requestStatus = true AND lotName = ?");
  }

  public List<Member> retrieveMembersInLot(String lotName) throws SQLException, IFT287Exception {
    List<Member> members = new ArrayList<>();
    retrieveMembersInLotStatement.setString(1, lotName);

    ResultSet results = retrieveMembersInLotStatement.executeQuery();
    while (results.next()) {
      members.add(instantiateEntity(results));
    }

    return members;
  }
}