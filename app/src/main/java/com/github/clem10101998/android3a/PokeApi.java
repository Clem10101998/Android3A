package com.github.clem10101998.android3a;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeApi {

    @GET("/api/v2/pokemon")
    Call<RestPokemonResponse> getPokemonResponse();
}
