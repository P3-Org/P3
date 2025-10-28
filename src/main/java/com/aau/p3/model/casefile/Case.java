package com.aau.p3.model.casefile;
import java.util.Date;


public class Case {
    private Date openedDate;
    private Date closedDate;
    private StatusEnum status;
    private String type;

    //Constructor
    public Case(Date openedDate, Date closedDate, StatusEnum status, String type) {
        this.openedDate = openedDate;
        this.closedDate = closedDate;
        this.status = status;
        this.type = type;
    }

    //Getters
    public Date getOpenedData() {
        return this.openedDate;
    }

    public Date getClosedDate() {
        return this.closedDate;
    }

    public StatusEnum getStatus() {
        return this.status;
    }

    public String getType() {
        return this.type;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
