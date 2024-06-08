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
import nam.tran.ui.state.UIState

abstract class BaseViewModel : ViewModel() {

    private val _stateFlow = MutableStateFlow<UIState>(UIState.Nothing())
    val stateFlow: SharedFlow<UIState> = _stateFlow

    abstract fun onLoad(bundle: Bundle?, isRefresh: Boolean)

    open fun <T> execution(flow: Flow<T>, onSuccess: ((T?) -> Unit)? = null) {
        _stateFlow.value = UIState.Loading()
        viewModelScope.launch {
            flow.catch {exception ->
                _stateFlow.value = UIState.Error(error = exception, retry = {
                    execution(flow,onSuccess)
                })
            }.collect {
                _stateFlow.value = UIState.Success()
                onSuccess?.invoke(it)
            }
        }
    }
}