package JardinCollectif.repositories;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.IsRegisteredTo;

import javax.persistence.TypedQuery;

public class IsRegisteredToRepository extends Repository<IsRegisteredTo> {
  private final TypedQuery<Long> countMemberRegisteredToALotStatement;
  private final TypedQuery<Long> countMembershipInLotStatement;
  private final TypedQuery<Long> deleteRequestToJoinLotStatement;

  public IsRegisteredToRepository(Connexion connexion) throws ClassNotFoundException, IFT287Exception {
    super(connexion);

    countMemberRegisteredToALotStatement = connexion.getEntityManager()
        .createQuery("SELECT COUNT(r.memberId) FROM RequestToJoin r WHERE r.memberId = ?1", Long.class);
    countMembershipInLotStatement = connexion.getEntityManager()
        .createQuery("SELECT COUNT(r.lotName) FROM RequestToJoin r WHERE r.lotName = ?1", Long.class);
    deleteRequestToJoinLotStatement = connexion.getEntityManager()
        .createQuery("DELETE FROM RequestToJoin r WHERE r.lotName = ?1", Long.class);
  }

  public boolean isMemberRegisteredToALot(int memberId) {
    countMemberRegisteredToALotStatement.setParameter(1, memberId);
    Long result = countMemberRegisteredToALotStatement.getSingleResult();
    return result > 0;
  }

  public int countMembershipInLot(String lotName) {
    countMembershipInLotStatement.setParameter(1, lotName);
    Long result = countMembershipInLotStatement.getSingleResult();
    return result.intValue();
  }

  public int deleteRequestToJoinLot(String lotName) {
    deleteRequestToJoinLotStatement.setParameter(1, lotName);
    return deleteRequestToJoinLotStatement.executeUpdate();
  }
}