package nam.tran.ui.extension

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun Fragment.showKeyboard(){
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun Fragment.hideKeyboard(){
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
}

fun FragmentManager.getRootFragment(): Fragment? {
    if (fragments.size > 0){
        return fragments[0]
    }
    return null
}