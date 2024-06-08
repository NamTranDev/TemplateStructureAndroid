package nam.tran.ui.loading

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import nam.tran.ui.R
import nam.tran.ui.databinding.IncludeProgressLayoutBinding
import java.lang.ref.WeakReference

open class LoadingDialog(private val mActivity: WeakReference<AppCompatActivity>) :
    LoadingController {

    private var dialog: Dialog? = null

    init {
        mActivity.get()?.run {
            dialog = Dialog(this, R.style.LoadingTheme)

            val window = dialog?.window
            window?.setGravity(Gravity.CENTER)
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            )
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND
            )
            window?.attributes?.dimAmount = 0.5f
            dialog?.setCanceledOnTouchOutside(false)
            dialog?.setCancelable(false)
            dialog?.setContentView(initLayout(this))
        }
    }

    override fun isShowing(): Boolean {
        return dialog?.isShowing == true
    }

    override fun initLayout(activity: AppCompatActivity): View {
        val binding = IncludeProgressLayoutBinding.inflate(LayoutInflater.from(activity),null,false)

        binding.progressLoading.visibility = View.VISIBLE
        return binding.root
    }

    override fun showDialog() {
        try {
            if (mActivity.get()?.isFinishing == false && !isShowing()) {
                dialog?.show()
            }
        } catch (_: Exception) {
        }
    }

    override fun hideDialog() {
        try {
            dialog?.dismiss()
        } catch (ignored: Exception) {
        }
    }

    override fun cancelDialog() {
        try {
            dialog?.cancel()
            dialog = null
        } catch (ignored: Exception) {
        }
    }
}
