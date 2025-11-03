package com.aau.p3;

import com.aau.p3.dawa.DawaAutocomplete;
import com.aau.p3.dawa.DawaPolygonForAddress;
import com.aau.p3.utility.UrlHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Take an address as input with scanner
        Scanner adresseScan = new Scanner(System.in);
        String adresseSearch = adresseScan.nextLine();
        System.out.println(adresseSearch);

        // Create Autocomplete object
        List<String> temp = new ArrayList<String>();
        temp.add("10.30458828");
        temp.add("57.33442822");

        DawaAutocomplete autocomplete = new DawaAutocomplete();
        List<String> addressInfo = autocomplete.autocomplete(adresseSearch);
        System.out.println(addressInfo);

        // Print possible addresses
        for (String address : addressInfo) {
            System.out.println(address);
        }


        DawaPolygonForAddress heyo = new DawaPolygonForAddress();
        heyo.getPropertyNo(addressInfo); // change to addressInfo when Dawa works
        heyo.getPolygon();

        }
    }