package com.github.clem10101998.android3a;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
//private static final String BASE_URL = "https://pokeapi.co/";
   private static final String BASE_URL = "https://raw.githubusercontent.com/Clem10101998/Android3A/master/";
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
        //showList();
        sharedPreferences = getSharedPreferences("application_esiea", Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();
        /*List<Pokemon> pokemonList = getDataFromCache();
        if ( pokemonList!= null){
        showList(pokemonList);
        }else{
            makeApiCall();
        }*/
        makeApiCall();
    }

    /*private List<Pokemon> getDataFromCache() {
        sharedPreferences
                .edit()
                .putString("jsonPokemonList", jsonString)
                .apply();
        String jsonString = gson.toJson(pokemonList);
    }*/

    private void showList(List<Pokemon> pokemonList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        /*List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }*/
        // define an adapter
        mAdapter = new ListAdapter(pokemonList);
        recyclerView.setAdapter(mAdapter);

    }

    private void makeApiCall(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PokeApi pokeApi = retrofit.create(PokeApi.class);
    //Log.d("Clem", "BEFORE CALLBACK");
        Call<RestPokemonResponse> call = pokeApi.getPokemonResponse();
        call.enqueue(new Callback<RestPokemonResponse>() {
            @Override
            public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {

                //Log.d("Clem", "INSIDE CALLBACK");
                if(response.isSuccessful() && (response.body() != null)) {

                    List<Pokemon> pokemonList = response.body().getResults();
                    //Toast.makeText(getApplicationContext(), "API Success", Toast.LENGTH_SHORT).show();
                    saveList(pokemonList);
                    showList(pokemonList);
                }else{
                    showError();
                    }
                }


            @Override
            public void onFailure(Call<RestPokemonResponse> call, Throwable t) {

                showError();
            }
        });
//Log.d("Clem", "AFTER CALLBACK");
    }

    private void saveList(List<Pokemon> pokemonList) {
       String jsonString = gson.toJson(pokemonList);
        sharedPreferences
                .edit()
                .putString("jsonPokemonList", jsonString)
                .apply();
        Toast.makeText(getApplicationContext(), "List saved", Toast.LENGTH_SHORT).show();
    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

}
