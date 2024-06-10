package nam.tran.data.usecase.pokemon_list

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nam.tran.data.model.response.pokemon_list.PokemonInfo
import nam.tran.data.network.api.PokemonApi
import java.util.regex.Pattern
import javax.inject.Inject

class PokemonListUseCaseImpl @Inject constructor(val api: PokemonApi) : PokemonListUseCase {
    override suspend fun getListPokemon(limit: Int, offset: Int): List<PokemonInfo> {
//        delay(10000)
        return api.getPokemonList(limit = limit,offset = offset).results.map {
            it.id = extractIdFromUrl(it.url)
            it.image =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${it.id}.png"
            it
        }
    }

    private fun extractIdFromUrl(url: String?): Int? {
        val pattern = Pattern.compile(".*/(\\d+)/")
        val matcher = pattern.matcher(url ?: "")
        return if (matcher.find()) {
            matcher.group(1)?.toInt()
        } else {
            null
        }
    }
}