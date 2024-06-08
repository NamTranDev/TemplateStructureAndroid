package nam.tran.ui.view

import nam.tran.ui.model.UIErrorRender

interface BehaviorActionController {
    fun showDialogLoading()
    fun hideDialogLoading()
    fun alertError(data : UIErrorRender?)
}