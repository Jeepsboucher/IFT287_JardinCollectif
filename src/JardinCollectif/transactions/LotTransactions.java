package JardinCollectif.transactions;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.IsSowedIn;
import JardinCollectif.model.Lot;
import JardinCollectif.repositories.IsRegisteredToRepository;
import JardinCollectif.repositories.IsSowedInRepository;
import JardinCollectif.repositories.LotRepository;

import java.sql.SQLException;
import java.util.List;

public class LotTransactions {
  private Connexion connexion;

  private LotRepository lotRepository;
  private IsSowedInRepository isSowedInRepository;
  private IsRegisteredToRepository isRegisteredToRepository;

  public LotTransactions(Connexion connexion, LotRepository lotRepository, IsSowedInRepository isSowedInRepository, IsRegisteredToRepository isRegisteredToRepository) {
    this.connexion = connexion;
    this.lotRepository = lotRepository;
    this.isSowedInRepository = isSowedInRepository;
    this.isRegisteredToRepository = isRegisteredToRepository;
  }

  public void addLot(String lotName, int maxMembercount) throws SQLException, IFT287Exception {
    try {
      if (lotName == null || lotName.isEmpty()) {
        throw new IFT287Exception("Le lot doit avoir un nom.");
      }

      if (lotRepository.exists(lotName)) {
        throw new IFT287Exception("Un lot ayant ce nom existe déjà.");
      }

      if (maxMembercount < 1) {
        throw new IFT287Exception("Le lot doit pouvoir être assigné à au moins un membre.");
      }

      Lot newLot = new Lot(lotName, maxMembercount);
      lotRepository.create(newLot);

      connexion.commit();
    } catch (Exception e) {
      connexion.rollback();
      throw e;
    }
  }

  public void removeLot(String lotName) throws SQLException, IFT287Exception {
    try {
      if (lotName == null || lotName.isEmpty()) {
        throw new IFT287Exception("Le lot spécifié doit avoir un nom.");
      }

      if (!lotRepository.exists(lotName)) {
        throw new IFT287Exception("Le lot spécifié n'existe pas.");
      }

      if (!isSowedInRepository.retrieveFromLot(lotName).isEmpty()) {
        throw new IFT287Exception("Le lot spécifié contient encore des plantes.");
      }

      isRegisteredToRepository.deleteRequestToJoinLot(lotName);
      lotRepository.delete(lotName);

      connexion.commit();
    } catch (Exception e) {
      connexion.rollback();
      throw e;
    }
  }

  public List<Lot> getLots() throws SQLException, IFT287Exception {
    return lotRepository.retrieveAll();
  }

  public List<IsSowedIn> getPlantsInLot(String lotName) throws SQLException, IFT287Exception {
    if (lotName == null || lotName.isEmpty()) {
      throw new IFT287Exception("Le lot spécifié doit avoir un nom.");
    }

    if (!lotRepository.exists(lotName)) {
      throw new IFT287Exception("Le lot spécifié n'existe pas.");
    }

    return isSowedInRepository.retrieveFromLot(lotName);
  }
}