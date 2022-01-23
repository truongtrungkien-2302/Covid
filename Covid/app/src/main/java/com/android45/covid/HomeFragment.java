package com.android45.covid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView rcvCountries;
    List<Country> countryList;
    CountryAdapter countryAdapter;
    View view;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        rcvCountries = view.findViewById(R.id.rcvCountries);
        countryList = new ArrayList<>();
        mainActivity = (MainActivity) getActivity();

        getCovidAPI();

        countryAdapter = new CountryAdapter(getActivity(), countryList, getActivity());
        rcvCountries.setLayoutManager(new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false));
        rcvCountries.setAdapter(countryAdapter);

        return view;
    }

    private void getCovidAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://demo0952224.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MediaAPI mediaAPI = retrofit.create(MediaAPI.class);
        Call<List<Country>> call = mediaAPI.getCovid();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                List<Country> countries = new ArrayList<>();
                countries = response.body();

                for (Country country : countries) {
                    countryList.add(country);
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {

            }
        });

    }
}