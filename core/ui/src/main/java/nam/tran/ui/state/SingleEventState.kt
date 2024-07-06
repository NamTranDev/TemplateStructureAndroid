package nam.tran.ui.state

sealed class SingleEventState<T>(val data: T? = null) {
    class Nothing<T> : SingleEventState<T>()
    class SingleAction<T>(data: T?) : SingleEventState<T>(data)
}