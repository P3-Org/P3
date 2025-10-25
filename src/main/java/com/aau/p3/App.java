package com.aau.p3;
import com.aau.p3.YearInFiveYears;

public class App {
    public static void main( String[] args )
    {
        System.out.println( "Hello World!");

        YearInFiveYears yearFive = new YearInFiveYears(new FetchAPI());
        int year = yearFive.CalcYear();
        System.out.println(year);
    }

}

