package JardinCollectif;

import Integer;
import JardinCollectif.transactions.LotTransactions;
import JardinCollectif.transactions.MemberTransactions;
import java.util.Date;
import String;
import JardinCollectif.transactions.PlantTransactions;

public class JardinCollectifCommandHandler {
  /* {src_lang=Java}*/


  private final Connexion connexion;

  private final MemberTransactions memberTransactions;

  private final LotTransactions lotTransactions;

  private final PlantTransactions plantTransactions;

  protected JardinCollectifCommandHandler(Connexion connexion) {
  return null;
  }

  private void addMember(Integer memberId, String firstName, String lastName, String password) {
  }

  private void removeMember(Integer memberId) {
  }

  private void promoteToAdmin(Integer memberId) {
  }

  private void requestToJoinLot(Integer memberId, String lotName) {
  }

  private void acceptRequestToJoinLot(String lotName, Integer memberId) {
  }

  private void denyRequestToJoinLot(String lotName, Integer memberId) {
  }

  private void addLot(String lotName, Integer maxMembercount) {
  }

  private void deleteLot(String lotName) {
  }

  private void addPlant(String plantName, Integer cultivationTime) {
  }

  private void removePlant(String plantName) {
  }

  private void sowPlantInLot(String plantName, String lotName, Integer memberId, Integer quantity, Date plantingDate) {
  }

  private void harvestPlant(String plantName, String lotName, Integer memberId) {
  }

  private void displayMembers() {
  }

  private void displayLots() {
  }

  private void displayPlants() {
  }

  private void displayPlantsInLot(String lotName) {
  }

}