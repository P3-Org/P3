package com.aau.p3.model.casefile;

public class Specialist {
    private String name;
    private TypeEnum type;
    private String email;
    private int CPR;
    private int phoneNumber;

    public Specialist(String name, TypeEnum type, String email, int CPR, int phoneNumber) {
        this.name = name;
        this.type = type;
        this.email = email;
        this.CPR = CPR;
        this.phoneNumber = phoneNumber;
    }

    public String getName(){
        return this.name;
    }

    public TypeEnum getType(){
        return this.type;
    }

    public String getEmail() {
        return this.email;
    }

    public int getCPR(){
        return this.CPR;
    }

    public int getPhoneNumber() {
        return this.phoneNumber;
    }

}
