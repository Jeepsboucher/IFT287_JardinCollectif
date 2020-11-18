package JardinCollectif.transactions;

import JardinCollectif.IFT287Exception;
import JardinCollectif.model.Lot;
import JardinCollectif.model.Member;
import JardinCollectif.model.RequestToJoin;
import JardinCollectif.repositories.LotRepository;
import JardinCollectif.repositories.MemberRepository;
import JardinCollectif.repositories.RequestToJoinRepository;

import java.sql.SQLException;
import java.util.List;

public class MemberTransactions {

  private final MemberRepository memberRepository;
  private final LotRepository lotRepository;
  private final RequestToJoinRepository requestToJoinRepository;

  public MemberTransactions(MemberRepository memberRepository, LotRepository lotRepository,
      RequestToJoinRepository requestToJoinRepository) {
    this.memberRepository = memberRepository;
    this.lotRepository = lotRepository;
    this.requestToJoinRepository = requestToJoinRepository;
  }

  public void addMember(long memberId, String firstName, String lastName, String password)
      throws SQLException, IFT287Exception {
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
    } catch (Exception e) {
      throw e;
    }
  }

  public void removeMember(long memberId) throws SQLException, IFT287Exception {
    try {

      Member toDelete = memberRepository.retrieve(memberId);
      if (toDelete == null) {
        throw new IFT287Exception("Le membre spécifié n'existe pas.");
      }

      if (requestToJoinRepository.isMemberRegisteredToALot(memberId)) {
        throw new IFT287Exception("Le membre spécifié est le dernier membre d'un lot.");
      }

      memberRepository.delete(toDelete);
    } catch (Exception e) {
      throw e;
    }
  }

  public void promoteToAdmin(long memberId) throws SQLException, IFT287Exception {
    try {

      if (!memberRepository.exists(memberId)) {
        throw new IFT287Exception("Le membre spécifié n'existe pas.");
      }

      Member toUpdate = memberRepository.retrieve(memberId);
      if (toUpdate.isAdmin) {
        throw new IFT287Exception("Le membre spécifié est déjà administrateur.");
      }

      toUpdate.isAdmin = true;
      memberRepository.update(toUpdate);

    } catch (Exception e) {
      throw e;
    }
  }

  public void requestToJoinLot(long memberId, String lotName) throws SQLException, IFT287Exception {
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

      if (requestToJoinRepository.exists(memberId, lotName)) {
        throw new IFT287Exception("Le membre spécifié a déjà une requête pour rejoindre le lot spécifié.");
      }

      Lot lot = lotRepository.retrieve(lotName);
      if (requestToJoinRepository.countMembershipInLot(lotName) == lot.maxMemberCount) {
        throw new IFT287Exception(
            "Nombre maximum de membre inscrit au lot atteint. Veuillez refuser les demandes en cours ou retirer des membres au lot.");
      }

      RequestToJoin newRequestToJoin = new RequestToJoin(memberId, lotName, false);
      requestToJoinRepository.create(newRequestToJoin);

    } catch (Exception e) {
      throw e;
    }
  }

  public void acceptRequestToJoinLot(String lotName, long memberId) throws SQLException, IFT287Exception {
    try {

      if (lotName == null || lotName.isEmpty()) {
        throw new IFT287Exception("Le lot spécifié doit avoir un nom.");
      }

      Lot lot = lotRepository.retrieve(lotName);
      if (lot == null) {
        throw new IFT287Exception("Le lot spécifié n'existe pas.");
      }

      Member member = memberRepository.retrieve(memberId);
      if (member == null) {
        throw new IFT287Exception("Le membre spécifié n'existe pas.");
      }

      if (!requestToJoinRepository.exists(memberId, lotName)) {
        throw new IFT287Exception("La requête pour rejoindre le lot spécifié n'existe pas.");
      }

      RequestToJoin updatedRequestToJoin = new RequestToJoin(memberId, lotName, true);
      requestToJoinRepository.update(updatedRequestToJoin);

    } catch (Exception e) {
      throw e;
    }
  }

  public void denyRequestToJoinLot(String lotName, long memberId) throws SQLException, IFT287Exception {
    try {

      if (lotName == null || lotName.isEmpty()) {
        throw new IFT287Exception("Le lot spécifié doit avoir un nom.");
      }

      Lot lot = lotRepository.retrieve(lotName);
      if (lot == null) {
        throw new IFT287Exception("Le lot spécifié n'existe pas.");
      }

      Member member = memberRepository.retrieve(memberId);
      if (member == null) {
        throw new IFT287Exception("Le membre spécifié n'existe pas.");
      }

      if (!requestToJoinRepository.exists(memberId, lotName)) {
        throw new IFT287Exception("La requête pour rejoindre le lot spécifié n'existe pas.");
      }

      requestToJoinRepository.delete(memberId, lotName);

    } catch (Exception e) {
      throw e;
    }
  }

  public List<Member> getMembers() throws SQLException, IFT287Exception {
    return memberRepository.retrieveAll();
  }

  public List<Member> getMembersInLot(String lotName) throws SQLException, IFT287Exception {
    if (lotName == null || lotName.isEmpty()) {
      throw new IFT287Exception("Le lot spécifié doit avoir un nom.");
    }

    Lot lot = lotRepository.retrieve(lotName);
    if (lot == null) {
      throw new IFT287Exception("Le lot spécifié n'existe pas.");
    }

    return requestToJoinRepository.retrieveMembersInLot(lotName);
  }
}