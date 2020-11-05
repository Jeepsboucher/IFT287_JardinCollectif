package JardinCollectif.transactions;

import JardinCollectif.Connexion;
import JardinCollectif.IFT287Exception;
import JardinCollectif.repositories.*;
import JardinCollectif.model.Plant;
import JardinCollectif.model.IsSowedIn;
import java.sql.SQLException;
import java.time.Instant;
import java.sql.Date;
import java.util.List;

public class PlantTransactions {
  private Connexion connexion;

  private PlantRepository plantRepository;
  private LotRepository lotRepository;
  private MemberRepository memberRepository;
  private IsSowedInRepository isSowedInRepository;
  private IsRegisteredToRepository isRegisteredToRepository;

  public PlantTransactions(Connexion connexion, PlantRepository plantRepository, LotRepository lotRepository, MemberRepository memberRepository, IsSowedInRepository isSowedInRepository, IsRegisteredToRepository isRegisteredToRepository) {
    this.connexion = connexion;
    this.plantRepository = plantRepository;
    this.lotRepository = lotRepository;
    this.memberRepository = memberRepository;
    this.isSowedInRepository = isSowedInRepository;
    this.isRegisteredToRepository = isRegisteredToRepository;
  }

  public void addPlant(String plantName, int cultivationTime) throws SQLException, IFT287Exception {
    try {
      connexion.getTransaction().begin();

      if (plantName == null || plantName.isEmpty()){
        throw new IFT287Exception("La plante doit avoir un nom.");
      }

      if (plantRepository.exists(plantName)){
        throw new IFT287Exception("Une plante ayant ce nom existe déjà.");
      }

      if (cultivationTime < 1) {
        throw new IFT287Exception("Le temps de culture doit être d'au moins une journée.");
      }

      Plant newPlant = new Plant(plantName, cultivationTime);
      plantRepository.create(newPlant);

      connexion.getTransaction().commit();
    } finally {
      if (connexion.getTransaction().isActive())
        connexion.getTransaction().rollback();
    }
  }

  public void removePlant(String plantName) throws SQLException, IFT287Exception {
    try {
      connexion.getTransaction().begin();

      if (plantName == null || plantName.isEmpty()){
        throw new IFT287Exception("La plante spécifié doit avoir un nom.");
      }

      Plant toDelete = plantRepository.retrieve(plantName);
      if (toDelete == null){
        throw new IFT287Exception("La plante spécifié n'existe pas.");
      }

      if (!isSowedInRepository.retrieveFromPlant(plantName).isEmpty()) {
        throw new IFT287Exception("La plante spécifé est encore en culture.");
      }

      plantRepository.delete(toDelete);

      connexion.getTransaction().commit();
    } finally {
      if (connexion.getTransaction().isActive())
        connexion.getTransaction().rollback();
    }
  }

  public void sowPlantInLot(String plantName, String lotName, int memberId, int quantity, Date plantingDate) throws SQLException, IFT287Exception {
    try {
      connexion.getTransaction().begin();

      if (plantName == null || plantName.isEmpty()){
        throw new IFT287Exception("La plante spécifié doit avoir un nom.");
      }

      if (!plantRepository.exists(plantName)){
        throw new IFT287Exception("La plante spécifié n'existe pas.");
      }

      if (lotName == null || lotName.isEmpty()) {
        throw new IFT287Exception("Le lot doit avoir un nom.");
      }

      if (!lotRepository.exists(lotName)) {
        throw new IFT287Exception("Le lot spécifié n'existe pas.");
      }

      if (!memberRepository.exists(memberId)) {
        throw new IFT287Exception("Le membre spécifié n'existe pas.");
      }

      if (quantity < 1) {
        throw new IFT287Exception("La quantité doit être d'au moins un.");
      }

      if (!isRegisteredToRepository.exists(memberId, lotName) || !isRegisteredToRepository.retrieve(memberId, lotName).requestStatus) {
        throw new IFT287Exception("Le membre spécifié n'a pa accès au lot spécifié.");
      }
      
      //Id will be ignored since it's auto-generated.
      IsSowedIn newIsSowedIn = new IsSowedIn(-1, quantity, plantingDate, memberId, lotName, plantName);
      isSowedInRepository.create(newIsSowedIn);

      connexion.getTransaction().commit();
    } finally {
      if (connexion.getTransaction().isActive())
        connexion.getTransaction().rollback();
    }
  }

  public void harvestPlant(String plantName, String lotName, int memberId) throws SQLException, IFT287Exception {
    try {
      connexion.getTransaction().begin();

      if (plantName == null || plantName.isEmpty()) {
        throw new IFT287Exception("La plante doit avoir un nom.");
      }

      if (!plantRepository.exists(plantName)) {
        throw new IFT287Exception("La plante spécifiée n'existe pas.");
      }

      if (lotName == null || lotName.isEmpty()) {
        throw new IFT287Exception("Le lot doit avoir un nom.");
      }

      if (!lotRepository.exists(lotName)) {
        throw new IFT287Exception("Le lot spécifié n'existe pas.");
      }
      
      if (!memberRepository.exists(memberId)) {
        throw new IFT287Exception("Le membre spécifié n'existe pas.");
      }

      if (!isRegisteredToRepository.exists(memberId, lotName) || !isRegisteredToRepository.retrieve(memberId, lotName).requestStatus) {
        throw new IFT287Exception("Le membre spécifié n'a pa accès au lot spécifié.");
      }

      Plant plant = plantRepository.retrieve(plantName);
      Date plantationDate = new Date(java.util.Date.from(Instant.now().minusSeconds(plant.cultivationTime * 24 * 60 * 60)).getTime());

      boolean hasHarvestedSomething = isSowedInRepository.deletePlantsOlderThanWithNameInLot(plantationDate, plantName, lotName) > 0;
      if (!hasHarvestedSomething) {
        throw new IFT287Exception("Aucun exemplaire de la plante spécifiée n'est prêt à être récolté.");
      }

      connexion.getTransaction().commit();
    } finally {
      if (connexion.getTransaction().isActive())
        connexion.getTransaction().rollback();
    }
  }

  public List<Plant> getPlants() throws SQLException, IFT287Exception {
    return plantRepository.retrieveAll();
  }

  public int getQuantitySowed(String plantName) throws SQLException, IFT287Exception {
    if (plantName == null || plantName.isEmpty()) {
      throw new IFT287Exception("La plante spécifité doit avoir un nom");
    }

    if (!plantRepository.exists(plantName)) {
      throw new IFT287Exception("La plante spécifié n'existe pas");
    }

    return isSowedInRepository.getQuantitySowed(plantName);
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
  
  public Date getHarvestDate(IsSowedIn isSowedIn) throws IFT287Exception, SQLException {
    if (isSowedIn.plantName == null || isSowedIn.plantName.isEmpty()) {
      throw new IFT287Exception("La plante spécifié doit avoir un nom.");
    }

    if (!plantRepository.exists(isSowedIn.plantName)) {
      throw new IFT287Exception("La plante spécifié n'existe pas.");
    }

    Plant plant = plantRepository.retrieve(isSowedIn.plantName);
    Date harvestDate = Date.valueOf(isSowedIn.plantingDate.toLocalDate().plusDays(plant.cultivationTime));

    return harvestDate;
  }
}