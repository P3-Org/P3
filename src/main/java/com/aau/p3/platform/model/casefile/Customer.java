package com.aau.p3.platform.model.casefile;

/**
 * Class customer that is used for mocking the customers.
 */
public class Customer {
    private final String name;
    private final int CPR;
    private final int phoneNumber;
    private final String email;

    /**
     * Constructor for Customer
     * @param name of the customer
     * @param CPR number
     * @param phoneNumber number
     * @param email of the customer
     */
    public Customer(String name, int CPR, int phoneNumber, String email) {
        this.name = name;
        this.CPR = CPR;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * Name getter of the customer
     * @return the string containing their name
     */
    public String getName() {
        return this.name;
    }

    /**
     * CPR number getter of the customer
     * @return the int containing the customers CPR number
     */
    public int getCPR() {
        return this.CPR;
    }

    /**
     * Phone number getter of the customer
     * @return the int containing the customers registered phone number
     */
    public int getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Getter for the customers email address
     * @return string of the email address
     */
    public String getEmail() {
        return this.email;
    }
}