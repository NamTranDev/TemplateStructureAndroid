package nam.tran.ui.view

import androidx.lifecycle.LifecycleOwner
import nam.tran.common.error.CommonErrorException
import nam.tran.ui.model.UIErrorRender

interface ViewLoadingController : LifecycleOwner {

    fun showDialogLoading()

    fun hideDialogLoading()

    fun onShowDialogError(
        renderUI : UIErrorRender?
    )
}
