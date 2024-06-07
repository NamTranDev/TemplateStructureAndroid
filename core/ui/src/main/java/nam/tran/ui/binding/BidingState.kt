package nam.tran.ui.binding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import nam.tran.common.state.State
import nam.tran.ui.model.UIErrorRender
import nam.tran.ui.view.BaseActivity

object BidingState {

    @JvmStatic
    @BindingAdapter("app:visibleView")
    fun visibleView(view: View, state: State?) {
        when (state) {
            is State.Error -> {
                (view.context as? BaseActivity)?.alert(UIErrorRender(error = state.error))
            }
            is State.Loading -> {
                view.visibility = View.GONE
            }
            is State.Success -> view.visibility = View.VISIBLE
            else -> {}
        }
    }

    @JvmStatic
    @BindingAdapter("app:refreshView")
    fun refreshView(view: SwipeRefreshLayout, state: State?) {
        when (state) {
            is State.Loading -> view.isRefreshing = true
            is State.Error, is State.Success -> view.isRefreshing = false
            else -> {}
        }
    }

    @JvmStatic
    @BindingAdapter("app:visibleProgress")
    fun visibleProgress(view: View, state: State?) {
        when (state) {
            is State.Error, is State.Success -> view.visibility = View.GONE
            is State.Loading -> {
                view.visibility = View.VISIBLE
            }

            else -> {}
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["app:visibleTextError"],
        requireAll = false
    )
    fun visibleTextError(text: TextView, state: State?) {
        when (state) {
            is State.Error -> {
                text.visibility = View.VISIBLE
                text.text = state.error?.message
            }
            is State.Loading, is State.Success -> text.visibility = View.GONE
            else -> {}
        }
    }

    @JvmStatic
    @BindingAdapter("app:visibleButtonError")
    fun visibleButtonError(bt: View, state: State?) {
        when (state) {
            is State.Error -> {
                if (state.hasRefresh) {
                    bt.visibility = View.GONE
                } else {
                    bt.visibility = View.VISIBLE
                    bt.setOnClickListener {
                        state.retry?.invoke()
                    }
                }
            }
            is State.Loading, is State.Success -> bt.visibility = View.GONE
            else -> {}
        }
    }
}
