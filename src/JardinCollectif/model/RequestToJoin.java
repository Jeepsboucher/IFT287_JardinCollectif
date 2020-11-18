package JardinCollectif.model;

import org.bson.Document;

import JardinCollectif.annotations.Column;
import JardinCollectif.annotations.Entity;
import JardinCollectif.annotations.Initializer;

@Entity
public class RequestToJoin {
    @Column(primaryKey = true)
    public long memberId;

    @Column(primaryKey = true)
    public String lotName;

    @Column
    public boolean requestStatus;

    @Initializer
    public RequestToJoin(Document d) {
        this.memberId = d.getLong("memberId");
        this.lotName = d.getString("lotName");
        this.requestStatus = d.getBoolean("requestStatus");
    }

    public RequestToJoin(long memberId, String lotName, boolean requestStatus) {
        this.memberId = memberId;
        this.lotName = lotName;
        this.requestStatus = requestStatus;
    }
}