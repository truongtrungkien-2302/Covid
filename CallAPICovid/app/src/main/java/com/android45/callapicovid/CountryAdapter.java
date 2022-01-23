package com.android45.callapicovid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder> implements Filterable {
    Context context;
    List<Country> countryList;
    Activity activity;

    public CountryAdapter(Context context, List<Country> countryList, Activity activity) {
        this.context = context;
        this.countryList = countryList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.icon_countries, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvCountry.setText(countryList.get(position).getCountry());
        holder.tvTotalConfirmed.setText(Integer.toString(countryList.get(position).getTotalConfirmed()));
        holder.tvTotalDeaths.setText(Integer.toString(countryList.get(position).getTotalDeaths()));

    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountry, tvTotalConfirmed, tvTotalDeaths;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            tvTotalConfirmed = itemView.findViewById(R.id.tvTotalConfirmed);
            tvTotalDeaths = itemView.findViewById(R.id.tvTotalDeaths);
        }
    }
}
