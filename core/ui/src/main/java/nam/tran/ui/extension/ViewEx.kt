package nam.tran.ui.extension

import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import nam.tran.common.Logger

fun View.singleClick(delayMillis: Long = 250, block: (View) -> Unit) {
    setOnClickListener {
        this.isEnabled = false
        block(it)
        postDelayed({ isEnabled = true }, delayMillis)
    }
}

fun View.safeNavigate(
    action: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    try {
        findNavController().navigate(action, args, navOptions, navigatorExtras)
    } catch (e: Exception) {
        Logger.enter(e)
    }
}