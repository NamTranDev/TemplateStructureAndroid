package nam.tran.ui.viewmodel

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nam.tran.ui.state.SingleEventState
import nam.tran.ui.state.TypeState
import nam.tran.ui.state.UIState

abstract class BaseViewModel : ViewModel() {

    //https://github.com/Kotlin/kotlinx.coroutines/issues/3002
    private val _eventUIState = MutableStateFlow<UIState>(UIState.Nothing())
    val eventUIState : StateFlow<UIState> get() = _eventUIState.asStateFlow()

    val stateObservable = ObservableField<UIState>()

    abstract fun onLoad(bundle: Bundle?, isRefresh: Boolean)

    open fun <T> execution(
        flow: Flow<T>,
        action: String = TypeState.DIALOG_STATE.action,
        onSuccess: ((T?) -> Unit)? = null,
    ) {
        viewModelScope.launch {
            _eventUIState.emit(UIState.Loading(action))
            flow.catch { exception ->
                _eventUIState.emit(UIState.Error(action = action, error = exception, retry = {
                    execution(flow, action, onSuccess)
                }))
            }.collect {
                _eventUIState.emit(UIState.Success(action = action))
                onSuccess?.invoke(it)
            }
        }
    }

    fun resetUiState() {
        _eventUIState.value = UIState.Nothing()
    }
}