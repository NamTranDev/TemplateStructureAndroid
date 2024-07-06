package nam.tran.ui.view

import android.graphics.Rect
import android.os.Bundle
import android.text.Html
import android.view.MotionEvent
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import nam.tran.common.Logger
import nam.tran.ui.R
import nam.tran.ui.extension.getRootFragment
import nam.tran.ui.extension.forceHideKeyboard
import nam.tran.ui.view.loading.LoadingController
import nam.tran.ui.view.loading.LoadingDialog
import nam.tran.ui.model.UIErrorRender
import java.lang.ref.WeakReference

abstract class BaseActivity : AppCompatActivity(), BehaviorActionController {

    abstract val layoutId: Int

    open val loadingDialog: LoadingController by lazy {
       LoadingDialog(WeakReference(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        setContentView(layoutId)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragment = currentFragment()
                if (fragment is HandleBackController && fragment.isHandleBackPress) {
                    return
                } else {
                    isEnabled = false
                    onBackAction()
                    isEnabled = true
                }
            }
        })
    }

    open fun setStatusBar() {}

    open fun onBackAction(){}

    override fun onDestroy() {
        loadingDialog.cancelDialog()
        super.onDestroy()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        (currentFragment() as? TouchEventController)?.run {
            if (isHandleTouchEvent(ev)){
                if (ev?.action == MotionEvent.ACTION_DOWN) {
                    currentFocus?.run {
                        if (this is EditText) {
                            val outRect = Rect()
                            getGlobalVisibleRect(outRect)
                            if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                                timeDelayTouchOutSide()?.run {
                                    postDelayed({
                                        forceHideKeyboard()
                                        clearFocus()
                                    },this)
                                } ?: kotlin.run {
                                    forceHideKeyboard()
                                    clearFocus()
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun currentFragment(): Fragment? {
        return (supportFragmentManager.getRootFragment() as? NavHostFragment)?.childFragmentManager?.findFragmentById(
            R.id.nav_fragment
        )
    }

    override fun showDialogLoading() {
        loadingDialog.showDialog()
    }

    override fun hideDialogLoading() {
        loadingDialog.hideDialog()
    }

    override fun alertError(
        data : UIErrorRender?
    ) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage(
            Html.fromHtml(data?.error?.message ?: getString(R.string.error_unknown)).toString()
        )
        alertDialog.setCancelable(false)
        alertDialog.setOnDismissListener {
            data?.afterRender?.invoke()
        }
        if (data?.retry == null) {
            alertDialog.setPositiveButton(
                getString(R.string.text_ok)
            ) { dialog, _ ->
                run {
                    dialog.dismiss()
                    if (data?.closeActivity == true)
                        finish()
                }
            }
        } else {
            alertDialog.setNegativeButton(
                getString(R.string.text_cancel)
            ) { dialog, _ ->
                run {
                    dialog.dismiss()
                    if (data.closeActivity == true)
                        finish()
                }
            }
            alertDialog.setPositiveButton(
                getString(R.string.text_retry)
            ) { dialog, _ ->
                run {
                    dialog.dismiss()
                    data.retry.invoke()
                }
            }
        }

        try {
            runOnUiThread {
                alertDialog.create().show()
            }
        } catch (e: Exception) {
            Logger.debug(e)
        }
    }
}