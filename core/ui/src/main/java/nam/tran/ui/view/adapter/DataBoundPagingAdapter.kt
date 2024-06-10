package nam.tran.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import nam.tran.ui.R
import nam.tran.ui.databinding.ItemNetworkStateBinding
import nam.tran.ui.state.UIState

abstract class DataBoundPagingAdapter<T : Any>(
    diffCallback: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, DataBoundViewHolder<ViewDataBinding>>(
    diffCallback
) {
    abstract fun getItemType(): Int
    abstract fun onCreateItemHolder(parent: ViewGroup, viewType: Int): ViewDataBinding
    abstract fun onBindItemHolder(holder: DataBoundViewHolder<ViewDataBinding>, position: Int)

    private var networkState: UIState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<ViewDataBinding> {
        if (viewType == R.layout.item_network_state) {
            return DataBoundViewHolder(
                ItemNetworkStateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            val binding = onCreateItemHolder(parent,viewType)
            return DataBoundViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ViewDataBinding>, position: Int) {
        if (position == NO_POSITION){
            return
        }
        if (holder.binding is ItemNetworkStateBinding) {
            holder.binding.state = networkState
        } else {
            onBindItemHolder(holder, position)
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
            getItemType()
        } else {
            R.layout.item_network_state
        }
    }

    fun setNetworkState(newNetworkState: UIState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
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