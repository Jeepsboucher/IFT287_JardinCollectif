package JardinCollectif.repositories;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.Member;

public class MemberRepository extends Repository<Member> {
  public MemberRepository(Connexion connexion) throws ClassNotFoundException, IFT287Exception {
    super(connexion);
  }
}