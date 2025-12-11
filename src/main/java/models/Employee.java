package models;

import java.time.LocalDate;

public class Employee{
    private int id;
    private String name;
    private String role; // "admin", "seller", "trainer", "master", "director"
    private String email;
    private String hireDate;

    public Employee(String name, String role, String email) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.hireDate = LocalDate.now().toString();
    }

    // Getters и Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public String getRole() { return role; }
    public String getEmail() { return email; }
    public String getHireDate() { return hireDate; }
}