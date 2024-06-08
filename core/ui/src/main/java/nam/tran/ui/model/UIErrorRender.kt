package nam.tran.ui.model

import java.lang.Exception

data class UIErrorRender(
    val error: Throwable? = null,
    val closeActivity: Boolean = false,
    val retry: (() -> Unit)? = null,
    val afterRender: (() -> Unit)? = null,
    val more : Map<String,Any>? = null
)