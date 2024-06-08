package nam.tran.template_structure.view.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import nam.tran.template_structure.R
import nam.tran.template_structure.databinding.FragmentHomeBinding
import nam.tran.ui.view.BaseFragment
import nam.tran.ui.view.BaseFragmentVM

@AndroidEntryPoint
class HomeFragment : BaseFragmentVM<FragmentHomeBinding>(){

    override val mViewModel: HomeViewModel by viewModels<HomeViewModel>()

    override val layoutId: Int
        get() = R.layout.fragment_home

    override val isAnimationTransfer: Boolean
        get() = false
}