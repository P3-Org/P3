package com.aau.p3.model.property;
import com.aau.p3.model.common.Address;

public class Property {
    private Address address;
    private double value;
    private double area;

    public Property(double value, double area, Address address) {
        this.value = value;
        this.area = area;
        this.address = address;
    }

    public Address getAddress() {
        return this.address;
    }

    public double getArea() {
        return this.area;
    }
    
    public double getValue() {
        return this.value;
    }
    
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return (this.address + " med størrelse: " + this.area + " og værdi: " + this.value);
    }
}
