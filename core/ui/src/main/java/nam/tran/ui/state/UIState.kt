package nam.tran.ui.state

import java.lang.Exception

sealed class UIState(val action : String? = null){
    var initial = true
    var hasRefresh: Boolean = false

    class Nothing : UIState()
    class Loading(action: String? = null) : UIState(action)
    class Success(action: String? = null) : UIState(action)
    class Error(
        action: String? = null,
        val error: Throwable?,
        val retry: (() -> Unit)? = null
    ) : UIState(action)

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