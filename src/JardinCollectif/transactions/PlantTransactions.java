package JardinCollectif.transactions;

import JardinCollectif.IFT287Exception;
import JardinCollectif.model.IsSowedIn;
import JardinCollectif.model.Lot;
import JardinCollectif.model.Member;
import JardinCollectif.model.Plant;
import JardinCollectif.model.RequestToJoin;
import JardinCollectif.repositories.IsSowedInRepository;
import JardinCollectif.repositories.LotRepository;
import JardinCollectif.repositories.MemberRepository;
import JardinCollectif.repositories.PlantRepository;
import JardinCollectif.repositories.RequestToJoinRepository;

import java.util.Calendar;
import java.util.Date;
import java.time.Instant;
import java.util.List;

public class PlantTransactions {

  private final PlantRepository plantRepository;
  private final LotRepository lotRepository;
  private final MemberRepository memberRepository;
  private final IsSowedInRepository isSowedInRepository;
  private final RequestToJoinRepository requestToJoinRepository;

  public PlantTransactions(PlantRepository plantRepository, LotRepository lotRepository,
      MemberRepository memberRepository, IsSowedInRepository isSowedInRepository,
      RequestToJoinRepository requestToJoinRepository) {
    this.plantRepository = plantRepository;
    this.lotRepository = lotRepository;
    this.memberRepository = memberRepository;
    this.isSowedInRepository = isSowedInRepository;
    this.requestToJoinRepository = requestToJoinRepository;
  }

  public void addPlant(String plantName, int cultivationTime) throws IFT287Exception {
    try {
      if (plantName == null || plantName.isEmpty()) {
        throw new IFT287Exception("La plante doit avoir un nom.");
      }

      if (plantRepository.exists(plantName)) {
        throw new IFT287Exception("Une plante ayant ce nom existe déjà.");
      }

      if (cultivationTime < 1) {
        throw new IFT287Exception("Le temps de culture doit être d'au moins une journée.");
      }

      Plant newPlant = new Plant(plantName, cultivationTime);
      plantRepository.create(newPlant);

    } catch (Exception e) {
      throw e;
    }
  }

  public void removePlant(String plantName) throws IFT287Exception {
    try {
      if (plantName == null || plantName.isEmpty()) {
        throw new IFT287Exception("La plante spécifié doit avoir un nom.");
      }

      Plant toDelete = plantRepository.retrieve(plantName);
      if (toDelete == null) {
        throw new IFT287Exception("La plante spécifié n'existe pas.");
      }

      if (!isSowedInRepository.retrieveFromPlant(plantName).isEmpty()) {
        throw new IFT287Exception("La plante spécifé est encore en culture.");
      }

      plantRepository.delete(plantName);

    } catch (Exception e) {
      throw e;
    }
  }

  public void sowPlantInLot(String plantName, String lotName, long memberId, int quantity, Date plantingDate)
      throws IFT287Exception {
    try {
      if (plantName == null || plantName.isEmpty()) {
        throw new IFT287Exception("La plante spécifié doit avoir un nom.");
      }

      if (!plantRepository.exists(plantName)) {
        throw new IFT287Exception("La plante spécifié n'existe pas.");
      }

      if (lotName == null || lotName.isEmpty()) {
        throw new IFT287Exception("Le lot doit avoir un nom.");
      }

      Lot lot = lotRepository.retrieve(lotName);
      if (lot == null) {
        throw new IFT287Exception("Le lot spécifié n'existe pas.");
      }

      Member member = memberRepository.retrieve(memberId);
      if (member == null) {
        throw new IFT287Exception("Le membre spécifié n'existe pas.");
      }

      if (quantity < 1) {
        throw new IFT287Exception("La quantité doit être d'au moins un.");
      }

      RequestToJoin request = requestToJoinRepository.retrieve(memberId, lotName);
      if (request == null || !request.requestStatus) {
        throw new IFT287Exception("Le membre spécifié n'a pas accès au lot spécifié.");
      }

      IsSowedIn newIsSowedIn = new IsSowedIn(quantity, plantingDate, memberId, lotName, plantName);
      isSowedInRepository.create(newIsSowedIn);
    } catch (Exception e) {
      throw e;
    }
  }

  public void harvestPlant(String plantName, String lotName, long memberId) throws IFT287Exception {
    try {
      if (plantName == null || plantName.isEmpty()) {
        throw new IFT287Exception("La plante doit avoir un nom.");
      }

      if (!plantRepository.exists(plantName)) {
        throw new IFT287Exception("La plante spécifiée n'existe pas.");
      }

      if (lotName == null || lotName.isEmpty()) {
        throw new IFT287Exception("Le lot doit avoir un nom.");
      }

      Lot lot = lotRepository.retrieve(lotName);
      if (lot == null) {
        throw new IFT287Exception("Le lot spécifié n'existe pas.");
      }

      Member member = memberRepository.retrieve(memberId);
      if (member == null) {
        throw new IFT287Exception("Le membre spécifié n'existe pas.");
      }

      RequestToJoin request = requestToJoinRepository.retrieve(memberId, lotName);
      if (request == null || !request.requestStatus) {
        throw new IFT287Exception("Le membre spécifié n'a pas accès au lot spécifié.");
      }

      Plant plant = plantRepository.retrieve(plantName);
      Date plantationDate = new Date(
          java.util.Date.from(Instant.now().minusSeconds(plant.cultivationTime * 24 * 60 * 60)).getTime());

      boolean hasHarvestedSomething = isSowedInRepository.deletePlantsOlderThanWithNameInLot(plantationDate, plantName,
          lotName) > 0;
      if (!hasHarvestedSomething) {
        throw new IFT287Exception("Aucun exemplaire de la plante spécifiée n'est prêt à être récolté.");
      }
    } catch (Exception e) {
      throw e;
    }
  }

  public List<Plant> getPlants() throws IFT287Exception {
    return plantRepository.retrieveAll();
  }

  public long getQuantitySowed(String plantName) throws IFT287Exception {
    if (plantName == null || plantName.isEmpty()) {
      throw new IFT287Exception("La plante spécifité doit avoir un nom");
    }

    if (!plantRepository.exists(plantName)) {
      throw new IFT287Exception("La plante spécifié n'existe pas");
    }

    return isSowedInRepository.getQuantitySowed(plantName);
  }

  public List<IsSowedIn> getPlantsInLot(String lotName) throws IFT287Exception {
    if (lotName == null || lotName.isEmpty()) {
      throw new IFT287Exception("Le lot spécifié doit avoir un nom.");
    }

    if (!lotRepository.exists(lotName)) {
      throw new IFT287Exception("Le lot spécifié n'existe pas.");
    }

    return isSowedInRepository.retrieveFromLot(lotName);
  }

  public Date getHarvestDate(IsSowedIn isSowedIn) throws IFT287Exception {
    if (isSowedIn.plantName == null || isSowedIn.plantName.isEmpty()) {
      throw new IFT287Exception("La plante spécifié doit avoir un nom.");
    }

    if (!plantRepository.exists(isSowedIn.plantName)) {
      throw new IFT287Exception("La plante spécifié n'existe pas.");
    }

    Plant plant = plantRepository.retrieve(isSowedIn.plantName);

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(isSowedIn.plantingDate);
    calendar.add(Calendar.DATE, plant.cultivationTime); // This is why java isn't the best programming langage.
    Date harvestDate = calendar.getTime();

    return harvestDate;
  }
}