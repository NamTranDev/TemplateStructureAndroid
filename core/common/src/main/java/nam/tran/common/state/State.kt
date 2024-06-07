package nam.tran.common.state

import io.lifestyle.plus.core.state.LoadingType
import nam.tran.common.error.CommonErrorException

sealed class State {
    var initial = true
    var hasRefresh: Boolean = false

    class Loading(val type: LoadingType) : State()
    class Success(val type: LoadingType) : State()
    class Error(
        val type: LoadingType = LoadingType.LOADING_DIALOG,
        val error: CommonErrorException?,
        val retry: (() -> Unit)? = null
    ) : State()

    class LoadingPaging(val type: LoadingType) : State() {
        init {
            initial = false
        }
    }

    class SuccessPaging(val type: LoadingType) : State() {
        init {
            initial = false
        }
    }

    class ErrorPaging(
        val type: LoadingType = LoadingType.LOADING_DIALOG,
        val error: CommonErrorException?,
        val retry: (() -> Unit)? = null
    ) : State() {
        init {
            initial = false
        }
    }
}