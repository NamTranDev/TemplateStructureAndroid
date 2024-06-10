package nam.tran.template_structure.view.home

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import nam.tran.data.usecase._qualifier.HomeUseCaseQualifier
import nam.tran.data.usecase.pokemon_list.PokemonListUseCase
import nam.tran.ui.state.PagingUIState
import nam.tran.ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @HomeUseCaseQualifier val useCase: PokemonListUseCase
) : BaseViewModel() {

    private val _request = MutableStateFlow<Boolean?>(null)
    val request: StateFlow<Boolean?> = _request

    private val _pagingUIState = MutableStateFlow(PagingUIState())
    val pagingUIState: StateFlow<PagingUIState> = _pagingUIState

    val pager = _request.filterNotNull().flatMapLatest { _ ->
        Pager(PagingConfig(pageSize = 20)) {
            PokemonPagingSource(useCase, _pagingUIState)
        }.flow
    }.cachedIn(viewModelScope)

    override fun onLoad(bundle: Bundle?, isRefresh: Boolean) {
        if (request.value == null){
            _request.value = true
        }
    }
}