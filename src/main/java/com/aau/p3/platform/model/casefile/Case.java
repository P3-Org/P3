package com.aau.p3.platform.model.casefile;
import com.aau.p3.platform.utilities.StatusEnum;

public class Case {
    private int caseID;
    private String address;
    private String owner;
    private StatusEnum status;

    // Constructor
    public Case(int caseID, String address, Customer owner, StatusEnum status) {
        this.caseID = caseID;
        this.address = address;
        this.owner = owner.getName();
        this.status = status;
    }

    // Getters
    public int getCaseID() { return this.caseID; }
    public String getAddress() { return this.address; }
    public String getOwner() { return this.owner; }
    public StatusEnum getStatus() { return this.status; }
    // Setters
    public void setStatus(StatusEnum status) { this.status = status; }
}
