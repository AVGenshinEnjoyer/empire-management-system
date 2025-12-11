package models;

// models/Visit.java
public class Visit {
    private int id;
    private int clientId;
    private String visitDate;
    private String status; // "present", "absent"

    public Visit(int clientId, String visitDate, String status) {
        this.clientId = clientId;
        this.visitDate = visitDate;
        this.status = status;
    }

    // Getters и Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClientId() { return clientId; }
    public String getVisitDate() { return visitDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}