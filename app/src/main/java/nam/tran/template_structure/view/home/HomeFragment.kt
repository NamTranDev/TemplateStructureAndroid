package nam.tran.template_structure.view.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import dagger.hilt.android.AndroidEntryPoint
import nam.tran.common.Logger
import nam.tran.template_structure.R
import nam.tran.template_structure.databinding.FragmentHomeBinding
import nam.tran.ui.extension.observeFlow
import nam.tran.ui.state.UIState
import nam.tran.ui.view.BaseFragment
import nam.tran.ui.view.BaseFragmentVM

@AndroidEntryPoint
class HomeFragment : BaseFragmentVM<FragmentHomeBinding>() {

    override val mViewModel: HomeViewModel by viewModels<HomeViewModel>()

    override val layoutId: Int
        get() = R.layout.fragment_home

    override val isAnimationTransfer: Boolean
        get() = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding?.viewModel = mViewModel
        val adapter = PokemonAdapter()
        val layoutManager = GridLayoutManager(activity,2)
        layoutManager.spanSizeLookup = object : SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return when(adapter.getItemViewType(position)){
                    nam.tran.ui.R.layout.item_network_state -> 2
                    else -> 1
                }
            }

        }
        mViewBinding?.rvPokemon?.layoutManager = layoutManager
        mViewBinding?.rvPokemon?.adapter = adapter

        observeFlow(mViewModel.pager) {
            adapter.submitData(it)
        }

        observeFlow(mViewModel.pagingUIState) {
            it.currentState?.run {
                if (initial) {
                    mViewModel.stateObservable.set(this)
                } else {
                    adapter.setNetworkState(this)
                }
            }
        }
    }
}