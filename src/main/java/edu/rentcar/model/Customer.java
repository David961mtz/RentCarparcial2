package edu.rentcar.model;

public class Customer {
    private final String id;
    private String fullName;
    private String email;
    private boolean authorized;

    public Customer(String id, String fullName, String email, boolean authorized) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.authorized = authorized;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public String getAuthorizedLabel() {
        return authorized ? "Sí" : "No";
    }
}
