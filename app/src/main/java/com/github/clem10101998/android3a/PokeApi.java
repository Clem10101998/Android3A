package com.github.clem10101998.android3a;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeApi {
  @GET("fakespokemonapi.json")

    Call<RestPokemonResponse> getPokemonResponse();
}
