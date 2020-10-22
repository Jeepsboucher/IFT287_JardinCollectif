package JardinCollectif.transactions;

import Integer;
import JardinCollectif.Connexion;
import JardinCollectif.repositories.*;
import String;

import java.util.Date;

public class PlantTransactions {
  /* {src_lang=Java}*/


  private Connexion connexion;

  private PlantRepository plantRepository;

  private LotRepository lotRepository;

  private MemberRepository memberRepository;

  private IsSowedInRepository isSowedInRepository;

  private IsRegisteredToRepository isRegisteredToRepository;

  public PlantTransactions(Connexion connexion, PlantRepository plantRepository, LotRepository lotRepository, MemberRepository memberRepository, IsSowedInRepository isSowedInRepository, isRegisteredToRepositiory) {
    return null;
  }

  public void addPlant(String plantName, Integer cultivationTime) {
  }

  public void removePlant(String plantName) {
  }

  public void sowPlantInLot(String plantName, String lotName, Integer memberId, Integer quantity, Date plantingDate) {
  }

  public void harvestPlant(String plantName, String lotName, Integer memberId) {
  }

  public List<Plant> getPlants() {
    return null;
  }

}