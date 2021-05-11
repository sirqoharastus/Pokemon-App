package com.decagon.android.sq007

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiInterface {

//    @GET("api/v2/pokemon/ditto")
//    fun getPokemonData():Call<List<Pokemon>>
    @GET("{url}")
    fun getPokemon(@Path("url") url: String): Call<Pokemon>

    @GET("?offset = 0&limit=500")
    fun getPokemonData(): Call<PokemonData>
}
