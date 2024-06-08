package nam.tran.ui.view

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nam.tran.ui.extension.hideKeyboard
import nam.tran.ui.model.UIErrorRender
import nam.tran.ui.state.UIState
import nam.tran.ui.viewmodel.BaseViewModel

abstract class BaseFragmentVM<V : ViewDataBinding> : BaseFragment<V>(),ViewLoadingController {

    abstract val mViewModel : BaseViewModel

    override fun onRenderComplete(bundle: Bundle?, isRefresh: Boolean) {
        mViewModel.onLoad(bundle, isRefresh)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                mViewModel.stateFlow.collect{
                    renderState(it)
                }
            }
        }
    }

    open fun renderState(state : UIState?){
        when(state){
            is UIState.Error -> {
                hideDialogLoading()
                mViewBinding?.root?.post {
                    onShowDialogError(UIErrorRender(error = state.error, retry = state.retry))
                }
            }
            is UIState.Loading -> showDialogLoading()
            is UIState.Success -> hideDialogLoading()
            else -> {}
        }
    }

    override fun showDialogLoading() {
        hideKeyboard()
        (activity as? BaseActivity)?.showDialogLoading()
    }

    override fun hideDialogLoading() {
        (activity as? BaseActivity)?.hideDialogLoading()
    }

    override fun onShowDialogError(renderUI: UIErrorRender?) {
        (activity as? BaseActivity)?.alertError(renderUI)
    }
}