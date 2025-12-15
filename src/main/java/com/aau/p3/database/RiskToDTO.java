package com.aau.p3.database;

/**
 * DTO = Data only object. This class is used in the conversion from Risks to a list of data only objects, such that we can store the data in the propertyObjects
 * in the database. Previously, this was not possible, as you cannot store objects directly into a DB as they need to be values only.
 * This is part of the workaround to make it possible to store objects and all the associated values in the DB.
 */
public class RiskToDTO {
    public String description;
    public String riskType;
    public double measurementValue;
    public double normalizedValue;
    public double[] thresholds;
    public double[] RGB;
}
