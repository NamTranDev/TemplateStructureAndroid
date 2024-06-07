package nam.tran.ui.view

import android.os.Bundle

interface RenderController : HandleBackController, TouchEventController {
    val isAnimationTransfer: Boolean
    var isViewCreated: Boolean
    var isViewDestroyed: Boolean
    fun onRenderComplete(bundle: Bundle? = null, isRefresh: Boolean = false)
}