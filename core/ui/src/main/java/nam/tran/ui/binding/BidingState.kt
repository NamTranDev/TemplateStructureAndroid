package nam.tran.ui.binding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import nam.tran.ui.extension.singleClick
import nam.tran.ui.model.TypeStateView
import nam.tran.ui.model.UIErrorRender
import nam.tran.ui.state.UIState
import nam.tran.ui.view.BaseActivity
import nam.tran.ui.view.BehaviorActionController

object BidingState {

    @JvmStatic
    @BindingAdapter(
        value = ["app:state", "app:type_view"],
        requireAll = true
    )
    fun renderState(view: View?, state: UIState?, typeUI: TypeStateView?) {
        view ?: return
        state ?: return
        when (state) {
            is UIState.Loading -> {
                renderLoading(view,typeUI)
            }

            is UIState.Success -> {
                renderSuccess(view,typeUI)
            }

            is UIState.Error -> {
                renderError(view,state,typeUI)
            }

            else -> {}
        }
    }

    private fun renderLoading(view: View, typeUI: TypeStateView?) {
        when (typeUI) {
            TypeStateView.PROGRESS -> view.visibility = View.VISIBLE
            TypeStateView.CONTAIN_VIEW,
            TypeStateView.TEXT_ERROR,
            TypeStateView.BUTTON_ERROR -> view.visibility = View.GONE
            TypeStateView.REFRESH -> (view as? SwipeRefreshLayout)?.isRefreshing = true
            else -> {}
        }
    }

    private fun renderSuccess(view: View, typeUI: TypeStateView?) {
        when (typeUI) {
            TypeStateView.CONTAIN_VIEW -> view.visibility = View.VISIBLE
            TypeStateView.PROGRESS,
            TypeStateView.TEXT_ERROR,
            TypeStateView.BUTTON_ERROR -> view.visibility = View.GONE
            TypeStateView.REFRESH -> (view as? SwipeRefreshLayout)?.isRefreshing = false
            else -> {}
        }
    }

    private fun renderError(view: View, state: UIState.Error, typeUI: TypeStateView?) {
        when (typeUI) {
            TypeStateView.TEXT_ERROR -> {
                view.visibility = View.VISIBLE
                (view as? TextView)?.text = state.error?.message
            }
            TypeStateView.BUTTON_ERROR -> {
                view.visibility = View.VISIBLE
                view.singleClick {
                    state.retry?.invoke()
                }
            }
            TypeStateView.CONTAIN_VIEW,
            TypeStateView.PROGRESS -> view.visibility = View.GONE

            TypeStateView.REFRESH -> (view as? SwipeRefreshLayout)?.isRefreshing = false
            else -> {}
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["app:state_paging", "app:type_view"], requireAll = false
    )
    fun renderStatePaging(view: View?, state: UIState?, typeUI: TypeStateView?) {
        view ?: return
        state ?: return
        when (typeUI) {
            TypeStateView.PROGRESS -> {
                view.visibility = if (state is UIState.LoadingPaging) View.VISIBLE else View.GONE
            }

            TypeStateView.TEXT_ERROR -> {
                view.visibility = if (state is UIState.ErrorPaging) View.VISIBLE else View.GONE
                (view as? TextView)?.text = (state as? UIState.ErrorPaging)?.error?.message
            }

            TypeStateView.BUTTON_ERROR -> {
                view.visibility = if (state is UIState.ErrorPaging) View.VISIBLE else View.GONE
                view.singleClick {
                    (state as? UIState.ErrorPaging)?.retry?.invoke()
                }
            }

            else -> {}
        }
    }
}
