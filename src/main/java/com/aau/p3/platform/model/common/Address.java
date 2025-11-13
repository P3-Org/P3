package com.aau.p3.platform.model.common;


// might not need but we shall see
// Used in property and perhaps costumer and case
public class Address {
    private String country;
    private int zipCode;
    private String city;
    private String streetName;
    private String houseNumber;

    // Address constructor with everything
    public Address(String country, int zipCode, String city, String streetName, String houseNumber){
        this.country = country;
        this.zipCode = zipCode;
        this.city = city;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }

    // Address constructor simple
    public Address(String streetName, String houseNumber){
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }

    public String getCountry() {
        return this.country;
    }

    public int getZipCode(){
        return this.zipCode;
    }

    public String getCity(){
        return this.city;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public String getHouseNumber(){
        return this.houseNumber;
    }


    @Override
    public String toString() {
        return streetName + " " + houseNumber +
                (city != null ? ", " + city : "") +
                (zipCode != 0 ? " " + zipCode : "") +
                (country != null ? ", " + country : "");
    }

}
