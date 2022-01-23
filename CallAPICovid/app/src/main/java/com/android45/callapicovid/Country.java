package com.android45.callapicovid;

public class Country {
    String ID;
    String Country;
    int TotalConfirmed;
    int TotalDeaths;

    public Country(){

    }

    public Country(String ID, String country, int totalConfirmed, int totalDeaths) {
        this.ID = ID;
        Country = country;
        TotalConfirmed = totalConfirmed;
        TotalDeaths = totalDeaths;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public int getTotalConfirmed() {
        return TotalConfirmed;
    }

    public void setTotalConfirmed(int totalConfirmed) {
        TotalConfirmed = totalConfirmed;
    }

    public int getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        TotalDeaths = totalDeaths;
    }
}
