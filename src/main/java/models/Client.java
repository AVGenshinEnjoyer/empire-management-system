package models;
import java.time.LocalDate;

public class Client {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String registrationDate;
    private String subscriptionStatus;
    private double debt;

    public Client(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.registrationDate = LocalDate.now().toString();
        this.subscriptionStatus = "active";
        this.debt = 0;
    }

    // Геттеры и сеттеры
    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public String getPhone(){ return phone; }
    public void setPhone(String phone){ this.phone = phone; }

    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    public String getSubscriptionStatus(){ return subscriptionStatus; }
    public void setSubscriptionStatus(String status){ this.subscriptionStatus = status; }

    public double getDebt(){ return debt; }
    public void setDebt(double debt){ this.debt = debt; }

    public String getRegistrationDate(){ return registrationDate; }
}