package JardinCollectif.model;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

public class Member {
  public long memberId;

  public boolean isAdmin;

  public String firstName;

  public String lastName;

  public String password;

  public List<Lot> acceptedRegistrations;

  public List<Lot> pendingRegistrations;

  public Member(Document d) {
    this.memberId = d.getInteger("memberId");
    this.isAdmin = d.getBoolean("isAdmin");
    this.firstName = d.getString("firstName");
    this.lastName = d.getString("lastName");
    this.password = d.getString("password");
  }

  public Member(long memberId, boolean isAdmin, String firstName, String lastName, String password) {
    this.memberId = memberId;
    this.isAdmin = isAdmin;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;

    this.acceptedRegistrations = new LinkedList<>();
    this.pendingRegistrations = new LinkedList<>();
  }

  public Document toDocument() {
    return new Document().append("memberId", memberId).append("isAdmin", isAdmin).append("firstName", firstName)
        .append("lastName", lastName).append("password", password);
  }
}