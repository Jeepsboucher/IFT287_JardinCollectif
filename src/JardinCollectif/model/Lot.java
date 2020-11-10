package JardinCollectif.model;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

public class Lot {
  public String lotName;

  public int maxMemberCount;

  public List<Member> registrations;

  public List<Member> pendingRegistrations;

  public Lot(Document d) {
    this.lotName = d.getString("lotName");
    this.maxMemberCount = d.getInteger("maxMemberCount");
  }

  public Lot(String lotName, int maxMembercount) {
    this.lotName = lotName;
    this.maxMemberCount = maxMembercount;

    this.registrations = new LinkedList<>();
    this.pendingRegistrations = new LinkedList<>();
  }

  public Document toDocument() {
    return new Document().append("lotName", lotName).append("maxMemberCount", maxMemberCount);
  }
}