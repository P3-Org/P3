package com.aau.p3.model.property;
import com.aau.p3.model.common.Address;

public class Property {
    private Address address;
    private double value;
    private double area;

    public Property(double value, double area) {
        this.value = value;
        this.area = area;
        
    }
}
