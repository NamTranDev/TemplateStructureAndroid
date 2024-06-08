package nam.tran.ui.state

import java.lang.Exception

sealed class UIState {
    var initial = true
    var hasRefresh: Boolean = false

    class Nothing : UIState()
    class Loading : UIState()
    class Success : UIState()
    class Error(
        val error: Throwable?,
        val retry: (() -> Unit)? = null
    ) : UIState()

    class LoadingPaging : UIState() {
        init {
            initial = false
        }
    }

    class SuccessPaging : UIState() {
        init {
            initial = false
        }
    }

    class ErrorPaging(
        val error: Throwable?,
        val retry: (() -> Unit)? = null
    ) : UIState() {
        init {
            initial = false
        }
    }
}