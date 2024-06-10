package nam.tran.data.usecase.pokemon_list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nam.tran.data.model.response.pokemon_list.PokemonInfo
import nam.tran.data.network.api.PokemonApi
import javax.inject.Inject

class PokemonListUseCaseImpl @Inject constructor(val api: PokemonApi) : PokemonListUseCase {
    override fun getListPokemon(offset : Int): Flow<List<PokemonInfo>> {
        return flow {
            emit(api.getPokemonList(offset = offset).results)
        }
    }
}