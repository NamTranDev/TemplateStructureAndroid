package nam.tran.data.network.api

import nam.tran.data.model.response.pokemon_list.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int = 20, @Query("offset") offset: Int = 0): PokemonListResponse
}