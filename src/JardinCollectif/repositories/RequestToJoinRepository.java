package JardinCollectif.repositories;

import JardinCollectif.Connection;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.RequestToJoin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mongodb.client.MongoCursor;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;

public class RequestToJoinRepository extends Repository<RequestToJoin> {
    public RequestToJoinRepository(Connection connection) throws ClassNotFoundException, SQLException, IFT287Exception {
        super(connection);
    }

    public boolean isMemberRegisteredToALot(long memberId) throws SQLException {
        MongoCursor<Document> isRegisteredCollection = collection.find(eq("memberId", memberId)).iterator();
        boolean isRegistered = false;
        try {
            while (isRegisteredCollection.hasNext()) {
                isRegistered = true;
            }
        } finally {
            isRegisteredCollection.close();
        }
        return isRegistered;
    }

    public int countMembershipInLot(String lotName) throws SQLException {
        MongoCursor<Document> countCollection = collection.find(eq("lotName", lotName)).iterator();
        int membershipCount = 0;
        try {
            while (countCollection.hasNext()) {
                membershipCount++;
            }
        } finally {
            countCollection.close();
        }
        return membershipCount;
    }

    public long deleteRequestToJoinLot(String lotName) {
        return collection.deleteMany(eq("lotName", lotName)).getDeletedCount();
    }
}