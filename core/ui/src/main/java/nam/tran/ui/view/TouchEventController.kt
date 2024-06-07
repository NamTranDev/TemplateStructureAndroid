package nam.tran.ui.view

import android.view.MotionEvent

interface TouchEventController {
    fun isHandleTouchEvent(ev : MotionEvent?) : Boolean
    fun timeDelayTouchOutSide() : Long?
}