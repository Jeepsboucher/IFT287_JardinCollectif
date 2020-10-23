package JardinCollectif;

import JardinCollectif.repositories.IsRegisteredToRepository;
import JardinCollectif.repositories.IsSowedInRepository;
import JardinCollectif.repositories.LotRepository;
import JardinCollectif.repositories.MemberRepository;
import JardinCollectif.repositories.PlantRepository;
import JardinCollectif.transactions.LotTransactions;
import JardinCollectif.transactions.MemberTransactions;
import JardinCollectif.transactions.PlantTransactions;
import JardinCollectif.annotations.Command;
import JardinCollectif.model.Member;
import JardinCollectif.model.IsSowedIn;
import JardinCollectif.model.Lot;
import JardinCollectif.model.Plant;

import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public class JardinCollectifCommandHandler {
  private final Connexion connexion;

  private final MemberTransactions memberTransactions;
  private final LotTransactions lotTransactions;
  private final PlantTransactions plantTransactions;

  protected JardinCollectifCommandHandler(Connexion connexion)
      throws ClassNotFoundException, SQLException, IFT287Exception {
    this.connexion = connexion;

    MemberRepository memberRepository = new MemberRepository(connexion);
    LotRepository lotRepository = new LotRepository(connexion);
    PlantRepository plantRepository = new PlantRepository(connexion);
    IsRegisteredToRepository isRegisteredToRepository = new IsRegisteredToRepository(connexion);
    IsSowedInRepository isSowedInRepository = new IsSowedInRepository(connexion);

    memberTransactions = new MemberTransactions(connexion, memberRepository, lotRepository, isRegisteredToRepository);
    lotTransactions = new LotTransactions(connexion, lotRepository, isSowedInRepository);
    plantTransactions = new PlantTransactions(connexion, plantRepository, lotRepository, memberRepository,
        isSowedInRepository, isRegisteredToRepository);
  }

  @Command("inscrireMembre")
  private void addMember(int memberId, String firstName, String lastName, String password) throws SQLException, IFT287Exception {
    memberTransactions.addMember(memberId, firstName, lastName, password);
  }

  @Command("supprimerMembre")
  private void removeMember(int memberId) throws SQLException, IFT287Exception {
    memberTransactions.removeMember(memberId);
  }

  @Command("promouvoirAdministrateur")
  private void promoteToAdmin(int memberId) throws SQLException, IFT287Exception {
    memberTransactions.promoteToAdmin(memberId);
  }

  @Command("rejoindreLot")
  private void requestToJoinLot(int memberId, String lotName) throws SQLException, IFT287Exception {
    memberTransactions.requestToJoinLot(memberId, lotName);
  }

  @Command("accepterDemande")
  private void acceptRequestToJoinLot(String lotName, int memberId) throws SQLException, IFT287Exception {
    memberTransactions.acceptRequestToJoinLot(lotName, memberId);
  }

  @Command("refuserDemande")
  private void denyRequestToJoinLot(String lotName, int memberId) throws SQLException, IFT287Exception {
    memberTransactions.denyRequestToJoinLot(lotName, memberId);
  }

  @Command("ajouterLot")
  private void addLot(String lotName, int maxMembercount) throws SQLException, IFT287Exception {
    lotTransactions.addLot(lotName, maxMembercount);
  }

  @Command("supprimerLot")
  private void removeLot(String lotName) throws SQLException, IFT287Exception {
    lotTransactions.removeLot(lotName);
  }

  @Command("ajouterPlante")
  private void addPlant(String plantName, int cultivationTime) throws SQLException, IFT287Exception {
    plantTransactions.addPlant(plantName, cultivationTime);
  }

  @Command("retirerPlante")
  private void removePlant(String plantName) throws SQLException, IFT287Exception {
    plantTransactions.removePlant(plantName);
  }

  @Command("planterPlante")
  private void sowPlantInLot(String plantName, String lotName, int memberId, int quantity, Date plantingDate) throws SQLException, IFT287Exception {
    plantTransactions.sowPlantInLot(plantName, lotName, memberId, quantity, plantingDate);
  }

  @Command("recolterPlante")
  private void harvestPlant(String plantName, String lotName, int memberId) throws SQLException, IFT287Exception {
    plantTransactions.harvestPlant(plantName, lotName, memberId);
  }

  @Command("afficherMembres")
  private void displayMembers() throws SQLException, IFT287Exception {
    List<Member> members = memberTransactions.getMembers();
    for (Member member : members) {
      displayMember(member);
    }
  }

  @Command("afficherLots")
  private void displayLots() throws SQLException, IFT287Exception {
    List<Lot> lots = lotTransactions.getLots();
    for (Lot lot : lots) {
      System.out.println("Name : " + lot.lotName + " Max member count : " + lot.maxMemberCount);

      System.out.println("Members :");
      List<Member> members = memberTransactions.getMembersInLot(lot.lotName);
      for (Member member : members) {
        displayMember(member);
      }

      System.out.println();
    }
  }

  @Command("afficherPlantes")
  private void displayPlants() throws SQLException, IFT287Exception {
    List<Plant> plants = plantTransactions.getPlants();
    for (Plant plant : plants) {
      int quantitySowed = plantTransactions.getQuantitySowed(plant.plantName);
      System.out.println("Name : " + plant.plantName + " Quantity sowed : " + quantitySowed);
    }
  }

  @Command("afficherPlantesLot")
  private void displayPlantsInLot(String lotName) throws SQLException, IFT287Exception {
    List<IsSowedIn> plants = plantTransactions.getPlantsInLot(lotName);
    for (IsSowedIn plant : plants) {
      Date harvestDate = plantTransactions.getHarvestDate(plant);
      System.out.println("Name : " + plant.plantName + " Quantity : " + plant.quantity + " Planting date : "
          + plant.plantingDate.toString() + " Harvest date : " + harvestDate.toString());
    }
  }

  private static void displayMember(Member member) {
    System.out.println("Id : " + member.memberId + " Fullname : " + member.firstName + " " + member.lastName
        + " Password : " + member.password + " Admin : " + member.isAdmin);
  }

  @Command("quitter")
  private void close() throws SQLException {
    connexion.fermer();
    System.exit(0);
  }
}