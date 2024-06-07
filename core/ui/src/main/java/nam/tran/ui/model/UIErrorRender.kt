package nam.tran.ui.model

import nam.tran.common.error.CommonErrorException

data class UIErrorRender(
    val error: CommonErrorException? = null,
    val closeActivity: Boolean = false,
    val retry: (() -> Unit)? = null,
    val afterRender: (() -> Unit)? = null,
    val more : Map<String,Any>? = null
)