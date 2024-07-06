package nam.tran.ui.extension

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nam.tran.common.Logger
import nam.tran.ui.state.SingleEventState
import nam.tran.ui.state.UIState

fun Fragment.showKeyboard() {
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Fragment.hideKeyboard() {
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
}

fun FragmentManager.getRootFragment(): Fragment? {
    if (fragments.size > 0) {
        return fragments[0]
    }
    return null
}

fun <T> Fragment.observeFlow(flow: Flow<T>, collector: suspend (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest { data ->
                collector(data)
            }
        }
    }
}

fun <T> Fragment.observeSingleFlow(
    flow: Flow<SingleEventState<T>>,
    collector: (T?) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest { state ->
                when (state) {
                    is SingleEventState.Nothing -> {
                        // No action needed for nothing state
                    }
                    is SingleEventState.SingleAction -> {
                        collector(state.data)
                        // Reset state after collecting the data
                        (flow as? MutableStateFlow)?.value = SingleEventState.Nothing()
                    }
                }
            }
        }
    }
}

fun Fragment.safeNavigate(
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

fun Fragment.safePopNavigate(@IdRes destinationId: Int, inclusive: Boolean) {
    try {
        findNavController().popBackStack(destinationId, inclusive)
    } catch (e: Exception) {
        Logger.enter(e)
    }
}