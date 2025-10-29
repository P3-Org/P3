package com.aau.p3;

import com.aau.p3.dawa.DawaAutocomplete;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Take an address as input with scanner
        Scanner adresseScan = new Scanner(System.in);
        String adresseSearch = adresseScan.nextLine();
        System.out.println(adresseSearch);

        // Create Autocomplete object
        DawaAutocomplete autocomplete = new DawaAutocomplete();
        List<String> addressInfo = autocomplete.autocomplete(adresseSearch);

        // Print possible addresses
        for (String address : addressInfo) {
            System.out.println(address);
        }

    }
}