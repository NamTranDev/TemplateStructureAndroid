package nam.tran.template_structure.view.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.MutableStateFlow
import nam.tran.data.model.response.pokemon_list.PokemonInfo
import nam.tran.data.usecase.pokemon_list.PokemonListUseCase
import nam.tran.ui.state.PagingUIState
import nam.tran.ui.state.UIState

class PokemonPagingSource(
    private val useCase: PokemonListUseCase,
    private val _pagingUIState: MutableStateFlow<PagingUIState>
) : PagingSource<Int, PokemonInfo>() {
    override fun getRefreshKey(state: PagingState<Int, PokemonInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonInfo> {
        val offset = params.key ?: 0
        _pagingUIState.value =
            _pagingUIState.value.copy(currentState = if (offset == 0) UIState.Loading() else UIState.LoadingPaging())
        return try {
            val datas = useCase.getListPokemon(limit = params.loadSize, offset = offset)
            _pagingUIState.value = _pagingUIState.value.copy(
                currentState = if (offset == 0) UIState.Success() else UIState.SuccessPaging(),
                emptyData = if (offset == 0) datas.isEmpty() else false
            )
            LoadResult.Page(
                data = datas,
                prevKey = null,
                nextKey = datas.last().id
            )
        } catch (e: Exception) {
            _pagingUIState.value =
                _pagingUIState.value.copy(currentState = if (offset == 0) UIState.Error(error = e, retry = {
                    invalidate()
                }) else UIState.ErrorPaging(error = e, retry = {
                    invalidate()
                }))
            return LoadResult.Error(e)
        }
    }
}