package com.aau.p3.model.casefile;

public class Customer {
    public static class ValuationDocument {
    }
    private final String name;
    private final int CPR;
    private final int phoneNumber;
    private final String email;

    //Constructor
    public Customer(String name, int CPR, int phoneNumber, String email) {
        this.name = name;
        this.CPR = CPR;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters
    public String getName() {
        return this.name;
    }
    public int getCPR() {
        return this.CPR;
    }
    public int getPhoneNumber() {
        return this.phoneNumber;
    }
    public String getEmail() {
        return this.email;
    }

}

