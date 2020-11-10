package JardinCollectif.transactions;

import JardinCollectif.IFT287Exception;
import JardinCollectif.model.IsSowedIn;
import JardinCollectif.model.Lot;
import JardinCollectif.repositories.IsSowedInRepository;
import JardinCollectif.repositories.LotRepository;

import java.sql.SQLException;
import java.util.List;

public class LotTransactions {

  private final LotRepository lotRepository;
  private final IsSowedInRepository isSowedInRepository;

  public LotTransactions(LotRepository lotRepository, IsSowedInRepository isSowedInRepository) {
    this.lotRepository = lotRepository;
    this.isSowedInRepository = isSowedInRepository;
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

    } catch (Exception e) {
      throw e;
    }
  }

  public void removeLot(String lotName) throws SQLException, IFT287Exception {
    try {

      if (lotName == null || lotName.isEmpty()) {
        throw new IFT287Exception("Le lot spécifié doit avoir un nom.");
      }

      Lot toDelete = lotRepository.retrieve(lotName);
      if (toDelete == null) {
        throw new IFT287Exception("Le lot spécifié n'existe pas.");
      }

      if (!isSowedInRepository.retrieveFromLot(lotName).isEmpty()) {
        throw new IFT287Exception("Le lot spécifié contient encore des plantes.");
      }

      lotRepository.delete(toDelete);

    } catch (Exception e) {
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