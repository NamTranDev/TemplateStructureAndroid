package nam.tran.template_structure.view.home

import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import nam.tran.data.model.response.pokemon_list.PokemonInfo
import nam.tran.data.usecase._qualifier.HomeUseCaseQualifier
import nam.tran.data.usecase.pokemon_list.PokemonListUseCase
import nam.tran.ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @HomeUseCaseQualifier val useCase: PokemonListUseCase
) : BaseViewModel() {

    private val _dataFlow = MutableStateFlow<List<PokemonInfo>?>(null)
    val dataFlow: SharedFlow<List<PokemonInfo>?> = _dataFlow

    override fun onLoad(bundle: Bundle?, isRefresh: Boolean) {
        if (_dataFlow.value == null) {
            execution(flow = useCase.getListPokemon(0)) {
                _dataFlow.value = it
            }
        }
    }
}