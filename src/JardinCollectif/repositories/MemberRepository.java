package JardinCollectif.repositories;

import java.sql.SQLException;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.Member;

public class MemberRepository extends Repository<Member> {
  public MemberRepository(Connexion connexion) throws ClassNotFoundException, SQLException, IFT287Exception {
    super(connexion);
  }
}