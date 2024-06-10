package nam.tran.ui.view.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import nam.tran.ui.R
import nam.tran.ui.databinding.ItemNetworkStateBinding
import nam.tran.ui.state.UIState

abstract class DataBoundPagingAdapter<T : Any,V : ViewDataBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, DataBoundViewHolder<ViewDataBinding>>(
    diffCallback
) {
    abstract fun getItemType(): Int
    abstract fun onCreateItemHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): DataBoundViewHolder<V>

    abstract fun onBindItemHolder(holder: DataBoundViewHolder<V>, position: Int)

    private var networkState: UIState? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBoundViewHolder<ViewDataBinding> {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == R.layout.item_network_state) {
            DataBoundViewHolder(
                ItemNetworkStateBinding.inflate(inflater, parent, false)
            )
        } else {
            onCreateItemHolder(inflater, parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ViewDataBinding>, position: Int) {
        if (position == NO_POSITION) {
            return
        }
        if (holder.binding is ItemNetworkStateBinding) {
            holder.binding.state = networkState
        } else {
            @Suppress("UNCHECKED_CAST")
            onBindItemHolder(holder as DataBoundViewHolder<V>, position)
        }
        holder.binding.executePendingBindings()
    }

    private fun hasExtraRow() =
        networkState != null && !(networkState is UIState.Success || networkState is UIState.SuccessPaging)

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            getItemType()
        }
    }

    fun setNetworkState(newNetworkState: UIState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        Handler(Looper.getMainLooper()).post {
            if (hadExtraRow != hasExtraRow) {
                if (hadExtraRow) {
                    notifyItemRemoved(super.getItemCount())
                } else {
                    notifyItemInserted(super.getItemCount())
                }
            } else if (hasExtraRow && previousState != newNetworkState) {
                notifyItemChanged(itemCount - 1)
            }
        }
    }
}