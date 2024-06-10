package nam.tran.template_structure.view.home

import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import nam.tran.common.Logger
import nam.tran.data.usecase._qualifier.HomeUseCaseQualifier
import nam.tran.data.usecase.home.HomeUseCase
import nam.tran.ui.state.UIState
import nam.tran.ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @HomeUseCaseQualifier val useCase: HomeUseCase
) : BaseViewModel() {

    private val _dataFlow = MutableStateFlow<Boolean?>(null)
    val dataFlow: SharedFlow<Boolean?> = _dataFlow

    override fun onLoad(bundle: Bundle?, isRefresh: Boolean) {
        if (_dataFlow.value == null){
            execution(flow = useCase.testFlow()) {
                _dataFlow.value = it
            }
        }
    }
}