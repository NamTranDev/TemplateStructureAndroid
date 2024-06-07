package nam.tran.template_structure.view.home

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import nam.tran.template_structure.R
import nam.tran.template_structure.databinding.FragmentHomeBinding
import nam.tran.ui.view.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(){
    override val layoutId: Int
        get() = R.layout.fragment_home

    override val isAnimationTransfer: Boolean
        get() = false

    override fun onRenderComplete(bundle: Bundle?, isRefresh: Boolean) {

    }
}