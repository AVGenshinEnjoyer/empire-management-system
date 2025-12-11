package models;

// models/Training.java
public class Training {
    private int id;
    private String trainerName;
    private String dayOfWeek;
    private String time;
    private int maxClients;

    public Training(String trainerName, String dayOfWeek, String time, int maxClients) {
        this.trainerName = trainerName;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.maxClients = maxClients;
    }

    // Getters и Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTrainerName() { return trainerName; }
    public void setTrainerName(String name) { this.trainerName = name; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String day) { this.dayOfWeek = day; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getMaxClients() { return maxClients; }
    public void setMaxClients(int max) { this.maxClients = max; }
}