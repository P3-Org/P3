package com.aau.p3;


    public class YearInFiveYears {
        private final FetchAPI api;

        public YearInFiveYears(FetchAPI api) {
            this.api = api;
        }

        public int CalcYear() {
            int currentYear = api.fetchYear();
            return currentYear + 5;
        }
    }

