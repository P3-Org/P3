package com.aau.p3;

import com.aau.p3.dawa.Autocomplete;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner adresseScan = new Scanner(System.in);
        String adresseSearch = adresseScan.nextLine();
        System.out.println(adresseSearch);

        Autocomplete autocom = new Autocomplete();
        String hej = autocom.autocomplete(adresseSearch);
        System.out.println(hej);

    }
}