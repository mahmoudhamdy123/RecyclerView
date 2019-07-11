package com.example.football;

import android.content.ContentProvider;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.football.Adapter.MatchesAdapter;
import com.example.football.DataApi.Matches.Matchday;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MatchFragment extends Fragment {

    Retrofit retrofit = new Retrofit.Builder().baseUrl(Service.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(new Gson())).build();

    Service service=retrofit.create(Service.class);
    RecyclerView rvmatch;
    MatchesAdapter matchesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.matches_layout,container,false);


        rvmatch=(RecyclerView) view.findViewById(R.id.rv_match);

        service.getCurrentMatches().enqueue(new Callback<ArrayList<Matchday>>() {


            @Override
            public void onResponse(Call<ArrayList<Matchday>> call, Response<ArrayList<Matchday>> response) {

                matchesAdapter=new MatchesAdapter(response.body(), getContext());
                rvmatch.setAdapter(matchesAdapter);

                LinearLayoutManager mLayoutManager=
                        new LinearLayoutManager(getContext());

                rvmatch.setLayoutManager(mLayoutManager);
                  matchesAdapter.notifyDataSetChanged();

                Toast.makeText(getContext(), response.body().get(0).getMatchAwayteamName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ArrayList<Matchday>> call, Throwable t) {

                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

            }
        });


        return view;

    }


}
