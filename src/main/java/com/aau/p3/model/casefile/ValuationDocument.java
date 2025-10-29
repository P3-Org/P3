package com.aau.p3.model.casefile;

import java.util.Date;

public class ValuationDocument {
    private int sections;
    private Date lastChanged;

    public ValuationDocument(int sections, Date lastChanged) {
        this.sections = sections;
        this.lastChanged = lastChanged;
    }

    public int getSections() {return this.sections; }

    public Date getLastChanged() {return this.lastChanged; }

    public void setSections(int sections){
        this.sections = sections;
    }

    public void setLastChanged(Date lastChanged) {
        this.lastChanged = lastChanged;
    }
}
