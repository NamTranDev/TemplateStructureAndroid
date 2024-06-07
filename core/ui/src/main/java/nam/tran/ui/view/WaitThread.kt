package nam.tran.ui.view

import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

class WaitThread(private val fragment: WeakReference<Fragment>) : Thread() {

    private var isStopped: Boolean = false
    private val mObject = Object()

    override fun run() {
        fragment.get()?.run {
            val isViewCreated = (fragment as? RenderController)?.isViewCreated ?: true
            while (!isViewCreated && !isStopped) {
                try {
                    synchronized(mObject) {
                        mObject.wait()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            if (isStopped)
                return

            fragment.get()?.run {
                activity?.runOnUiThread {
                    (fragment as? RenderController)?.onRenderComplete(arguments,false)
                }
            }
        }
    }

    fun continueProcessing() {
        synchronized(mObject) {
            mObject.notifyAll()
        }
    }

    fun stopProcessing() {
        isStopped = true
        synchronized(mObject) {
            mObject.notifyAll()
        }
    }
}

