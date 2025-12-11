package models;// models/AuditLog.java
import java.time.LocalDateTime;

public class AuditLog {
    private int id;
    private String action;
    private String timestamp;

    public AuditLog(String action) {
        this.action = action;
        this.timestamp = LocalDateTime.now().toString();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAction() { return action; }
    public String getTimestamp() { return timestamp; }
}