package JardinCollectif.transactions;

import Integer;
import JardinCollectif.repositories.PlantRepository;
import java.util.Date;
import JardinCollectif.repositories.LotRepository;
import JardinCollectif.repositories.IsSowedInRepository;
import String;
import JardinCollectif.repositories.MemberRepository;
import JardinCollectif.repositories.IsRegisteredToRepository;
import JardinCollectif.Connexion;

public class PlantTransactions {
  /* {src_lang=Java}*/


  private Connexion connexion;

  private PlantRepository plantRepository;

  private LotRepository lotRepository;

  private MemberRepository memberRepository;

  private IsSowedInRepository isSowedInRepository;

  private IsRegisteredToRepository isRegisteredToRepository;

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

  public PlantTransactions(Connexion connexion, PlantRepository plantRepository, LotRepository lotRepository, MemberRepository memberRepository, IsSowedInRepository isSowedInRepository,  isRegisteredToRepositiory) {
  return null;
  }

}