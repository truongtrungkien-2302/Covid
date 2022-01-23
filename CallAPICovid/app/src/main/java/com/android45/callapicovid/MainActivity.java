package com.android45.callapicovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.app.SearchManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

import com.android45.callapicovid.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    SearchView searchView;
    List<Country> countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Covid-19");
        setSupportActionBar(toolbar);

        postponeEnterTransition();

        countryList = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                replaceSearchFragment(timKiem(countryList, s));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                replaceSearchFragment(timKiem(countryList, s));
                return false;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private List<Country> timKiem(List<Country> ls, String strSearch) {
        List<Country> tracks = new ArrayList<>();
        for (Country tracks1 : ls) {
            if (tracks1.getCountry().toLowerCase().contains(strSearch.toLowerCase())) {
                tracks.add(tracks1);
            }
        }
        return tracks;
    }

    private void replaceSearchFragment(List<Country> tracks) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.body_container, SearchFragment.getInstance(tracks));
        fragmentTransaction.addToBackStack(SearchFragment.TAG);
        fragmentTransaction.commit();
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }
}