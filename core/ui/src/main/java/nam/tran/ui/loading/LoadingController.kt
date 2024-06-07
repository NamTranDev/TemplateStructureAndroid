package nam.tran.ui.loading

import android.view.View
import androidx.appcompat.app.AppCompatActivity

interface LoadingController {
    fun initLayout(activity: AppCompatActivity): View
    fun isShowing(): Boolean
    fun showDialog()
    fun hideDialog()
    fun cancelDialog()
}