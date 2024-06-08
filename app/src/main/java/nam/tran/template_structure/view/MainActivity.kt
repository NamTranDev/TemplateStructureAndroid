package nam.tran.template_structure.view

import dagger.hilt.android.AndroidEntryPoint
import nam.tran.template_structure.R
import nam.tran.ui.view.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity(){
    override val layoutId: Int
        get() = R.layout.activity_main
}