package JardinCollectif.repositories;

import JardinCollectif.Connection;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.Member;

public class MemberRepository extends Repository<Member> {
  public MemberRepository(Connection connection) throws ClassNotFoundException, IFT287Exception {
    super(connection);
  }
}