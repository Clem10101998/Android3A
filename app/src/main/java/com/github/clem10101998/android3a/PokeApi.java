package com.github.clem10101998.android3a;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeApi {
  //@GET("fakespokemonapi.json")
 @GET("/api/v2/pokemon")
    Call<RestPokemonResponse> getPokemonResponse();
}
