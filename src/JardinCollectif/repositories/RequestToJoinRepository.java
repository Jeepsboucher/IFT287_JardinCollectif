package JardinCollectif.repositories;

import JardinCollectif.Connection;
import JardinCollectif.IFT287Exception;
import JardinCollectif.model.Member;
import JardinCollectif.model.RequestToJoin;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

public class RequestToJoinRepository extends Repository<RequestToJoin> {
  public RequestToJoinRepository(Connection connection) throws ClassNotFoundException, IFT287Exception {
    super(connection);
  }

  public boolean isMemberRegisteredToALot(long memberId) {
    return collection.find(eq("memberId", memberId)).first() != null;
  }

  public long countMembershipInLot(String lotName) {
    return collection.countDocuments(eq("lotName", lotName));
  }

  public long deleteRequestsToJoinLot(String lotName) {
    return collection.deleteMany(eq("lotName", lotName)).getDeletedCount();
  }

  public List<Member> retrieveMembersInLot(String lotName) {
    MongoCursor<Document> memberCollection = collection.aggregate(Arrays.asList(match(eq("requestStatus", true)),
            match(eq("lotName", lotName)), lookup("Member", "memberId", "memberId", "member"), unwind("$member"), replaceRoot("$member"))).iterator();

    List<Member> members = new LinkedList<Member>();
    try {
      while (memberCollection.hasNext()) {
        members.add(new Member(memberCollection.next()));
      }
    } finally {
      memberCollection.close();
    }

    return members;
  }
}