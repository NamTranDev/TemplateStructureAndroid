package nam.tran.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import nam.tran.common.Logger
import nam.tran.common.error.CommonErrorException
import nam.tran.ui.R
import nam.tran.ui.extension.autoCleared
import nam.tran.ui.model.UIErrorRender
import java.lang.ref.WeakReference

abstract class BaseFragment<V : ViewDataBinding> : Fragment(), RenderController,ViewLoadingController {

    protected abstract val layoutId: Int

    private var mWaitThread: WaitThread? = null
    protected var mViewBinding by autoCleared<V>()

    override val isAnimationTransfer: Boolean = true
    override var isViewCreated: Boolean = false
    override var isViewDestroyed: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.debug("Screen --- ",javaClass.name)
        mViewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return mViewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isAnimationTransfer) {
            view.postDelayed({
                onRenderComplete(arguments, false)
            }, timeDelay())
        } else {
            isViewCreated = true
            isViewDestroyed = false
            mWaitThread?.continueProcessing()
        }
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        var animation = super.onCreateAnimation(transit, enter, nextAnim)
        if (enter) {
            if (animation == null && nextAnim != 0) {
                animation = AnimationUtils.loadAnimation(activity, nextAnim)
            }

            view?.setLayerType(View.LAYER_TYPE_HARDWARE, null)

            animation?.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    view?.setLayerType(View.LAYER_TYPE_NONE, null)
                    if (isViewDestroyed)
                        return
                    if (mWaitThread == null) {
                        mWaitThread = WaitThread(WeakReference(this@BaseFragment))
                        mWaitThread?.start()
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }

        return animation
    }

    override val isHandleBackPress: Boolean
        get() = false

    override fun isHandleTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun timeDelayTouchOutSide(): Long? {
        return null
    }

    fun timeDelay(): Long {
        return resources.getInteger(R.integer.animation_time_full).toLong()
    }

    override fun showDialogLoading() {
        (activity as? BaseActivity)?.showDialogLoading()
    }

    override fun hideDialogLoading() {
        (activity as? BaseActivity)?.hideDialogLoading()
    }

    override fun onShowDialogError(renderUI : UIErrorRender?) {
        (activity as? BaseActivity)?.alert(renderUI)
    }
}