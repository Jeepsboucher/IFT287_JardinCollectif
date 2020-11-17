package JardinCollectif.repositories;

import JardinCollectif.Connection;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.IsSowedIn;

import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;

public class IsSowedInRepository extends Repository<IsSowedIn> {
  public IsSowedInRepository(Connection connection) throws ClassNotFoundException, SQLException, IFT287Exception {
    super(connection);
  }

  public List<IsSowedIn> retrieveFromPlant(String plantName) throws IFT287Exception {
    List<IsSowedIn> liste = new LinkedList<IsSowedIn>();
    MongoCursor<Document> retrieveCollection = collection.find(eq("plantName", plantName)).iterator();
    try {
      while (retrieveCollection.hasNext()) {
        liste.add(new IsSowedIn(retrieveCollection.next()));
      }
    } finally {
      retrieveCollection.close();
    }
    return liste;
  }

  public List<IsSowedIn> retrieveFromLot(String lotName) throws IFT287Exception {
    List<IsSowedIn> liste = new LinkedList<IsSowedIn>();
    MongoCursor<Document> retrieveCollection = collection.find(eq("lotName", lotName)).iterator();
    try {
      while (retrieveCollection.hasNext()) {
        liste.add(new IsSowedIn(retrieveCollection.next()));
      }
    } finally {
      retrieveCollection.close();
    }
    return liste;
  }

  public long deletePlantsOlderThanWithNameInLot(Date plantingDate, String plantName, String lotName)
      throws SQLException, IFT287Exception {
    return collection
        .deleteMany(and(eq("plantName", plantName), and(eq("lotName", lotName), eq("plantingDate", plantingDate))))
        .getDeletedCount();
  }

  public int getQuantitySowed(String plantName) throws IFT287Exception {
    int quantitySowed = 0;
    MongoCursor<Document> plantCollection = collection.find(eq("plantName", plantName)).iterator();
    try {
      while (plantCollection.hasNext()) {
        IsSowedIn sowed = new IsSowedIn(plantCollection.next());
        quantitySowed = quantitySowed + sowed.quantity;
      }
    } finally {
      plantCollection.close();
    }
    return quantitySowed;
  }
}