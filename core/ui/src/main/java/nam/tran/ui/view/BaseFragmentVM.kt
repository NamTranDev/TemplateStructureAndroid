package nam.tran.ui.view

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nam.tran.ui.extension.hideKeyboard
import nam.tran.ui.model.UIErrorRender
import nam.tran.ui.state.TypeState
import nam.tran.ui.state.UIState
import nam.tran.ui.viewmodel.BaseViewModel

abstract class BaseFragmentVM<V : ViewDataBinding> : BaseFragment<V>(), ViewLoadingController {

    abstract val mViewModel: BaseViewModel

    override fun onRenderComplete(bundle: Bundle?, isRefresh: Boolean) {
        mViewModel.onLoad(bundle, isRefresh)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.eventUIState.collectLatest { state ->
                    when(state){
                        is UIState.Nothing -> {
                            // No action
                        }
                        else -> {
                            renderState(state)
                            mViewModel.resetUiState()
                        }
                    }
                }
            }
        }
    }

    open fun renderState(state : UIState?){
		when (state?.action) {
                TypeState.DIALOG_STATE.action -> {
                    renderStateDialog(state = state)
                }

                TypeState.OBSERVABLE_STATE.action -> {
                    mViewModel.stateObservable.set(state)
                }

                else -> {
                    renderOtherState(state = state)
                }
            }
    }

    open fun renderStateDialog(state: UIState?) {
        when (state) {
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

    open fun renderOtherState(state: UIState?) {}

    override fun showDialogLoading() {
        hideKeyboard()
        (activity as? BehaviorActionController)?.showDialogLoading()
    }

    override fun hideDialogLoading() {
        (activity as? BehaviorActionController)?.hideDialogLoading()
    }

    override fun onShowDialogError(renderUI: UIErrorRender?) {
        (activity as? BehaviorActionController)?.alertError(renderUI)
    }
}