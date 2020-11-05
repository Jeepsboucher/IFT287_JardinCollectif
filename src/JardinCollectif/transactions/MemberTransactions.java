package JardinCollectif.transactions;

import java.sql.SQLException;
import java.util.List;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.IsRegisteredTo;
import JardinCollectif.model.Lot;
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

  public void addMember(long memberId, String firstName, String lastName, String password)
      throws SQLException, IFT287Exception {
    try {
      connexion.getTransaction().begin();

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
      connexion.getTransaction().commit();
    } finally {
      if (connexion.getTransaction().isActive())
        connexion.getTransaction().rollback();
    }
  }

  public void removeMember(long memberId) throws SQLException, IFT287Exception {
    try {
      connexion.getTransaction().begin();

      Member toDelete = memberRepository.retrieve(memberId);
      if (toDelete == null) {
        throw new IFT287Exception("Le membre spécifié n'existe pas.");
      }

      if (isRegisteredToRepository.isMemberRegisteredToALot(memberId)) {
        throw new IFT287Exception("Le membre spécifié est le dernier membre d'un lot.");
      }

      memberRepository.delete(toDelete);
      connexion.getTransaction().commit();
    } finally {
      if (connexion.getTransaction().isActive())
        connexion.getTransaction().rollback();
    }
  }

  public void promoteToAdmin(long memberId) throws SQLException, IFT287Exception {
    try {
      connexion.getTransaction().begin();

      if (!memberRepository.exists(memberId)) {
        throw new IFT287Exception("Le membre spécifié n'existe pas.");
      }

      Member toUpdate = memberRepository.retrieve(memberId);
      if (toUpdate.isAdmin) {
        throw new IFT287Exception("Le membre spécifié est déjà administrateur.");
      }

      toUpdate.isAdmin = true;

      connexion.getTransaction().commit();
    } finally {
      if (connexion.getTransaction().isActive())
        connexion.getTransaction().rollback();
    }
  }

  public void requestToJoinLot(long memberId, String lotName) throws SQLException, IFT287Exception {
    try {
      connexion.getTransaction().begin();

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

      Lot lot = lotRepository.retrieve(lotName);
      if (isRegisteredToRepository.countMembershipInLot(lotName) == lot.maxMemberCount) {
        throw new IFT287Exception(
            "Nombre maximum de membre inscrit au lot atteint. Veuillez refuser les demandes en cours ou retirer des membres au lot.");
      }

      IsRegisteredTo newIsRegisteredTo = new IsRegisteredTo(memberId, lotName, false);
      isRegisteredToRepository.create(newIsRegisteredTo);

      connexion.getTransaction().commit();
    } finally {
      if (connexion.getTransaction().isActive())
        connexion.getTransaction().rollback();
    }
  }

  public void acceptRequestToJoinLot(String lotName, Long memberId) throws SQLException, IFT287Exception {
    try {
      connexion.getTransaction().begin();

      if (lotName == null || lotName.isEmpty()) {
        throw new IFT287Exception("Le lot spécifié doit avoir un nom.");
      }

      IsRegisteredTo toUpdate = isRegisteredToRepository.retrieve(memberId, lotName);
      if (toUpdate == null) {
        throw new IFT287Exception("La requête pour rejoindre le lot spécifié n'existe pas.");
      }

      toUpdate.requestStatus = true;

      connexion.getTransaction().commit();
    } finally {
      if (connexion.getTransaction().isActive())
        connexion.getTransaction().rollback();
    }
  }

  public void denyRequestToJoinLot(String lotName, Long memberId) throws SQLException, IFT287Exception {
    try {
      connexion.getTransaction().begin();

      if (lotName == null || lotName.isEmpty()) {
        throw new IFT287Exception("Le lot spécifié doit avoir un nom.");
      }

      IsRegisteredTo toDelete = isRegisteredToRepository.retrieve(memberId, lotName);
      if (toDelete == null) {
        throw new IFT287Exception("La requête pour rejoindre le lot spécifié n'existe pas.");
      }

      isRegisteredToRepository.delete(toDelete);

      connexion.getTransaction().commit();
    } finally {
      if (connexion.getTransaction().isActive())
        connexion.getTransaction().rollback();
    }
  }

  public List<Member> getMembers() throws SQLException, IFT287Exception {
    return memberRepository.retrieveAll();
  }

  public List<Member> getMembersInLot(String lotName) throws SQLException, IFT287Exception {
    if (lotName == null || lotName.isEmpty()) {
      throw new IFT287Exception("Le lot spécifié doit avoir un nom.");
    }

    if (!lotRepository.exists(lotName)) {
      throw new IFT287Exception("Le lot spécifié n'existe pas.");
    }

    return memberRepository.retrieveMembersInLot(lotName);
  }
}