package com.android45.covid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MediaAPI {

    @GET("getCovid")
    Call<List<Country>> getCovid();
}
