package com.aau.p3.platform.model.casefile;

import com.aau.p3.platform.utilities.StatusEnum;

/**
 * Class case that is used for mocking the cases. More functionality can be added come future development.
 */
public class Case {
    private final int caseID;
    private final String address;
    private final Customer owner;
    private final StatusEnum status;

    /**
     * Constructor for case class
     * @param caseID ID of the case
     * @param address of the property for the case
     * @param owner of the property for the case
     * @param status of the case
     */
    public Case(int caseID, String address, Customer owner, StatusEnum status) {
        this.caseID = caseID;
        this.address = address;
        this.owner = owner;
        this.status = status;
    }

    /**
     * Case ID getter
     * @return the int corresponding to the caseID for the given case object
     */
    public int getCaseID() { return this.caseID; }

    /**
     * Address getter
     * @return the string corresponding to the address of the property
     */
    public String getAddress() { return this.address; }

    /**
     * Owner name of the property getter
     * @return string containing the owner of the property's name
     */
    public String getOwnerName() {
        return this.owner.getName();
    }

    /**
     * Getter for the status of the case
     * @return the status enum corresponding to that case
     */
    public StatusEnum getStatus() {
        return this.status;
    }
}
