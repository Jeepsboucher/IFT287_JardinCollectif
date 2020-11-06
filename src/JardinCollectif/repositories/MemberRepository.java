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
        "SELECT m FROM Member m, IsRegisteredTo r, Lot l "
            + "INNER JOIN m.memberId r "
            + "INNER JOIN r.lotName l "
            + "WHERE r.requestStatus = TRUE AND l.lotName = ?1",
        Member.class);
  }

  public List<Member> retrieveMembersInLot(String lotName) throws IFT287Exception {
    retrieveMembersInLotStatement.setParameter(1, lotName);
    List<Member> members = retrieveMembersInLotStatement.getResultList();
    return members;
  }
}