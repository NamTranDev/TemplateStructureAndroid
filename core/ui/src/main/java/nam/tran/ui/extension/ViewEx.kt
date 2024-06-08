package nam.tran.ui.extension

import android.view.View

fun View.singleClick(delayMillis: Long = 250, block: (View) -> Unit) {
    setOnClickListener {
        this.isEnabled = false
        block(it)
        postDelayed({ isEnabled = true }, delayMillis)
    }
}