package com.android45.callapicovid;

public class Global {
    String TotalConfirmed;
    String TotalDeaths;
    String TotalRecovered;

    public Global(String totalConfirmed, String totalDeaths, String totalRecovered) {
        TotalConfirmed = totalConfirmed;
        TotalDeaths = totalDeaths;
        TotalRecovered = totalRecovered;
    }

    public String getTotalConfirmed() {
        return TotalConfirmed;
    }

    public void setTotalConfirmed(String totalConfirmed) {
        TotalConfirmed = totalConfirmed;
    }

    public String getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        TotalDeaths = totalDeaths;
    }

    public String getTotalRecovered() {
        return TotalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        TotalRecovered = totalRecovered;
    }
}
