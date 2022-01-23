package com.android45.callapicovid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    List<Global> globalList;
    CountryAdapter countryAdapter;
    View view;
    MainActivity mainActivity;

    TextView tvTotalConfirmed, tvTotalDeaths, tvTotalRecovered;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        rcvCountries = view.findViewById(R.id.rcvCountries);
        tvTotalConfirmed = view.findViewById(R.id.tvTotalConfirmed);
        tvTotalDeaths = view.findViewById(R.id.tvTotalDeaths);
        tvTotalRecovered = view.findViewById(R.id.tvTotalRecovered);

        countryList = new ArrayList<>();
        globalList = new ArrayList<>();

        mainActivity = (MainActivity) getActivity();

//        getCovidAPI();
        getGlobalAPI();

        getListCovidFromRealTimeDatabase();


        countryAdapter = new CountryAdapter(getActivity(), countryList, getActivity());
        rcvCountries.setLayoutManager(new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false));
        rcvCountries.setAdapter(countryAdapter);
        return view;
    }

    private void getListCovidFromRealTimeDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_covid");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Country tracks = snapshot.getValue(Country.class);
                if (tracks != null) {
                    countryList.add(tracks);
                    countryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCovidAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://demo0952224.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CovidAPI mediaAPI = retrofit.create(CovidAPI.class);
        Call<List<Country>> call = mediaAPI.getCountry();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                List<Country> countries = new ArrayList<>();
                countries = response.body();
//
//                for (Country country : countries) {
//                    countryList.add(country);
//                    addCovidToRealtimeDatabase(country);
//                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {

            }
        });
    }

    private void getGlobalAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://demo0952224.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CovidAPI mediaAPI = retrofit.create(CovidAPI.class);
        Call<List<Global>> call = mediaAPI.getGlobal();
        call.enqueue(new Callback<List<Global>>() {
            @Override
            public void onResponse(Call<List<Global>> call, Response<List<Global>> response) {
                List<Global> countries = new ArrayList<>();
                countries = response.body();

                tvTotalConfirmed.setText("Total Confirmed: " + countries.get(0).getTotalConfirmed());
                tvTotalDeaths.setText("Total Deaths: " + countries.get(0).getTotalDeaths());
                tvTotalRecovered.setText("Total Recovered: " + countries.get(0).getTotalRecovered());

            }

            @Override
            public void onFailure(Call<List<Global>> call, Throwable t) {

            }
        });
    }

    private void addCovidToRealtimeDatabase(Country country) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("list_covid");

        String pathPbject = String.valueOf(country.getID());
        reference.child(pathPbject).setValue(country, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });
    }
}