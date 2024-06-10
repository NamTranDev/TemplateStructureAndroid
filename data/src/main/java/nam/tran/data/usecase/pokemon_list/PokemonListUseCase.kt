package nam.tran.data.usecase.pokemon_list

import kotlinx.coroutines.flow.Flow
import nam.tran.data.model.response.pokemon_list.PokemonInfo

interface PokemonListUseCase{
    fun getListPokemon(offset : Int = 0) : Flow<List<PokemonInfo>>
}