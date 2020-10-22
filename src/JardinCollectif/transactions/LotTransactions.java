package JardinCollectif.transactions;

import Integer;
import JardinCollectif.repositories.LotRepository;
import JardinCollectif.repositories.IsSowedInRepository;
import String;
import JardinCollectif.Connexion;

public class LotTransactions {
  /* {src_lang=Java}*/


  private Connexion connexion;

  private LotRepository lotRepository;

  private IsSowedInRepository isSowedInRepository;

  public LotTransactions(Connexion connexion, LotRepository lotRepository, IsSowedInRepository isSowedInRepository) {
  return null;
  }

  public void addLot(String lotName, Integer maxMembercount) {
  }

  public void deleteLot(String lotName) {
  }

  public List<Lot> getLots() {
  return null;
  }

  public List<IsSowedIn> getPlantsInLot(String lotName) {
  return null;
  }

}