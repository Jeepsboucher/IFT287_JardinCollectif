package JardinCollectif.repositories;

import java.util.List;

import javax.persistence.TypedQuery;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.Member;

public class MemberRepository extends Repository<Member> {
  private TypedQuery<Member> retrieveMembersInLotStatement;

  public MemberRepository(Connexion connexion) throws ClassNotFoundException, IFT287Exception {
    super(connexion);

    retrieveMembersInLotStatement = connexion.getEntityManager().createQuery(
        "SELECT m.memberId, m.isAdmin, m.firstName, m.lastName, m.password FROM Member m"
            + "INNER JOIN IsRegisteredTo ON m.memberId = IsRegisteredTo.memberId "
            + "INNER JOIN Lot ON Lot.lotName = IsRegisteredTo.lotName "
            + "WHERE IsRegisteredTo.requestStatus = TRUE AND Lot.lotName = ?",
        Member.class);
  }

  public List<Member> retrieveMembersInLot(String lotName) throws IFT287Exception {
    retrieveMembersInLotStatement.setParameter(1, lotName);
    List<Member> members = retrieveMembersInLotStatement.getResultList();
    return members;
  }
}