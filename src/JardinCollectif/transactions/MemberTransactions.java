package JardinCollectif.transactions;

import Integer;
import JardinCollectif.repositories.LotRepository;
import JardinCollectif.repositories.MemberRepository;
import String;
import JardinCollectif.repositories.IsRegisteredToRepository;
import JardinCollectif.Connexion;

public class MemberTransactions {
  /* {src_lang=Java}*/


  private Connexion connexion;

  private MemberRepository memberRepository;

  private LotRepository lotRepository;

  private IsRegisteredToRepository isRegisteredToRepository;

  public MemberTransactions(Connexion connexion, MemberRepository memberRepository, LotRepository lotRepository, IsRegisteredToRepository isRegisteredToRepository) {
  return null;
  }

  public void addMember(Integer memberId, String firstName, String lastName, String password) {
  }

  public void removeMember(Integer memberId) {
  }

  public void promoteToAdmin(Integer memberId) {
  }

  public void requestToJoinLot(String lotName, Integer memberId) {
  }

  public void acceptRequestToJoinLot(String lotName, Integer memberId) {
  }

  public void denyRequestToJoinLot(String lotName, Integer memberId) {
  }

  public List<Member> getMembers() {
  return null;
  }

}