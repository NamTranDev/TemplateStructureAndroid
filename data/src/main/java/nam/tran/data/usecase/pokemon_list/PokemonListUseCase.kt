package nam.tran.data.usecase.pokemon_list

import kotlinx.coroutines.flow.Flow
import nam.tran.data.model.response.pokemon_list.PokemonInfo

interface PokemonListUseCase{
    suspend fun getListPokemon(limit : Int = 20,offset : Int = 0) : List<PokemonInfo>
}