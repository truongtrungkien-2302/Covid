package com.android45.callapicovid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CovidAPI {
    @GET("getCovid")
    Call<List<Country>> getCountry();

    @GET("getGlobal")
    Call<List<Global>> getGlobal();
}
