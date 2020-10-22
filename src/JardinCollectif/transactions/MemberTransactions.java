package JardinCollectif.transactions;

import java.sql.SQLException;
import java.util.List;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.IsRegisteredTo;
import JardinCollectif.model.Member;
import JardinCollectif.repositories.IsRegisteredToRepository;
import JardinCollectif.repositories.LotRepository;
import JardinCollectif.repositories.MemberRepository;

public class MemberTransactions {
  private Connexion connexion;

  private MemberRepository memberRepository;

  private LotRepository lotRepository;

  private IsRegisteredToRepository isRegisteredToRepository;

  public MemberTransactions(Connexion connexion, MemberRepository memberRepository, LotRepository lotRepository,
      IsRegisteredToRepository isRegisteredToRepository) {
    this.connexion = connexion;
    this.memberRepository = memberRepository;
    this.lotRepository = lotRepository;
    this.isRegisteredToRepository = isRegisteredToRepository;
  }

  public void addMember(int memberId, String firstName, String lastName, String password) throws SQLException, IFT287Exception {
    try {
      if (firstName == null || firstName.isEmpty()) {
        throw new IFT287Exception("Le membre doit avoir un prénom.");
      }

      if (lastName == null || lastName.isEmpty()) {
        throw new IFT287Exception("Le membre doit avoir un nom de famille.");
      }

      if (password == null || password.isEmpty()) {
        throw new IFT287Exception("Le membre doit avoir un mot de passe.");
      }

      if (memberId < 1) {
        throw new IFT287Exception("L'id du membre doit être trictement positif.");
      }

      if (memberRepository.exists(memberId)) {
        throw new IFT287Exception("Un membre ayant cet id existe déjà.");
      }

      Member newMember = new Member(memberId, false, firstName, lastName, password);
      memberRepository.create(newMember);
      connexion.commit();
    } catch (Exception e) {
      connexion.rollback();
      throw e;
    }
  }

  public void removeMember(int memberId) throws SQLException, IFT287Exception {
    try {
      if (!memberRepository.exists(memberId)) {
        throw new IFT287Exception("Le membre spécifié n'existe pas.");
      }

      memberRepository.delete(memberId);
      connexion.commit();
    } catch (Exception e) {
      connexion.rollback();
      throw e;
    }
  }

  public void promoteToAdmin(int memberId) throws SQLException, IFT287Exception {
    try {
      if (!memberRepository.exists(memberId)) {
        throw new IFT287Exception("Le membre spécifié n'existe pas.");
      }

      if (!memberRepository.isAdmin(memberId)) {
        throw new IFT287Exception("Le membre soécifié est déjà administrateur.");
      }

      memberRepository.promoteToAdmin(memberId);
      connexion.commit();
    } catch (Exception e) {
      connexion.rollback();
      throw e;
    }
  }

  public void requestToJoinLot(int memberId, String lotName) throws SQLException, IFT287Exception {
    try {
      if (lotName == null || lotName.isEmpty()) {
        throw new IFT287Exception("Le lot spécifié doit avoir un nom.");
      }

      if (!lotRepository.exists(lotName)) {
        throw new IFT287Exception("Le lot spécifié n'existe pas.");
      }

      if (!memberRepository.exists(memberId)) {
        throw new IFT287Exception("Le membre spécifié n'existe pas.");
      }

      if (isRegisteredToRepository.exists(memberId, lotName)) {
        throw new IFT287Exception("Le membre spécifié a déjà une requête pour rejoindre le lot spécifié.");
      }

      IsRegisteredTo newIsRegisteredTo = new IsRegisteredTo(memberId, lotName, false);
      isRegisteredToRepository.create(newIsRegisteredTo);

      connexion.commit();
    } catch (Exception e) {
      connexion.rollback();
      throw e;
    }
  }

  public void acceptRequestToJoinLot(String lotName, int memberId) throws SQLException, IFT287Exception {
    try {
      if (lotName == null || lotName.isEmpty()) {
        throw new IFT287Exception("Le lot spécifié doit avoir un nom.");
      }

      if (!isRegisteredToRepository.exists(memberId, lotName)) {
        throw new IFT287Exception("La requête pour rejoindre le lot spécifié n'existe pas.");
      }

      IsRegisteredTo updatedIsRegisteredTo = new IsRegisteredTo(memberId, lotName, true);
      isRegisteredToRepository.update(updatedIsRegisteredTo);

      connexion.commit();
    } catch (Exception e) {
      connexion.rollback();
      throw e;
    }
  }

  public void denyRequestToJoinLot(String lotName, int memberId) throws SQLException, IFT287Exception {
    try {
      if (lotName == null || lotName.isEmpty()) {
        throw new IFT287Exception("Le lot spécifié doit avoir un nom.");
      }

      if (!isRegisteredToRepository.exists(memberId, lotName)) {
        throw new IFT287Exception("La requête pour rejoindre le lot spécifié n'existe pas.");
      }

      isRegisteredToRepository.delete(lotName, memberId);

      connexion.commit();
    } catch (Exception e) {
      connexion.rollback();
      throw e;
    }
  }

  public List<Member> getMembers() throws SQLException, IFT287Exception {
    try{
      return memberRepository.retrieveAll();
    } catch (Exception e) {
      connexion.rollback();
      throw e;
    }
  }
}