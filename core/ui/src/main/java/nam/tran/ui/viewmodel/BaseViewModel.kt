package nam.tran.ui.viewmodel

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import nam.tran.ui.state.TypeState
import nam.tran.ui.state.UIState

abstract class BaseViewModel : ViewModel() {

    private val _stateFlow = MutableStateFlow<UIState>(UIState.Nothing())
    val stateFlow: SharedFlow<UIState> = _stateFlow

    val stateObservable = ObservableField<UIState>()

    abstract fun onLoad(bundle: Bundle?, isRefresh: Boolean)

    open fun <T> execution(
        flow: Flow<T>,
        action: String = TypeState.DIALOG_STATE.action,
        onSuccess: ((T?) -> Unit)? = null,
    ) {
        _stateFlow.value = UIState.Loading(action)
        viewModelScope.launch {
            flow.catch { exception ->
                _stateFlow.value = UIState.Error(action = action, error = exception, retry = {
                    execution(flow, action, onSuccess)
                })
            }.collect {
                _stateFlow.value = UIState.Success(action)
                onSuccess?.invoke(it)
            }
        }
    }
}