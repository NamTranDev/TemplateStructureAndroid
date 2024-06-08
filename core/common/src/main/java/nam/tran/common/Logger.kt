package nam.tran.common

import android.content.Intent
import android.database.Cursor
import android.util.Log
import java.lang.StringBuilder

object Logger {
    private const val TAG = "NamTranDev"
    private val methodName: StackTraceElement?
        get() {
            val tracks = Thread.currentThread().stackTrace
            val STACK_TRACE_LEVELS_UP = 4
            return if (tracks.size > STACK_TRACE_LEVELS_UP) {
                tracks[STACK_TRACE_LEVELS_UP]
            } else null
        }

    private fun e(message: String, vararg objects: Any) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, String.format(message, *objects) + " /" + methodName)
        }
    }

    fun w(message: String?, vararg objects: Any?) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, String.format(message!!, *objects) + " /" + methodName)
        }
    }

    fun error(message: String?, vararg objects: Any?) {
        Log.e(TAG, String.format(message!!, *objects) + " /" + methodName)
    }

    fun dump(intent: Intent?) {
        if (intent == null) {
            e("Intent is null")
            return
        }
        e("Intent: action: %s", intent.action!!)
        if (intent.getPackage() != null) {
            e("  pkg: %s", intent.getPackage()!!)
        }
        if (intent.type != null) {
            e("  type: %s", intent.type!!)
        }
        if (intent.component != null) {
            e("  comp: %s", intent.component!!.flattenToString())
        }
        if (intent.dataString != null) {
            e("  data: %s", intent.dataString!!)
        }
        if (intent.categories != null) {
            for (cat in intent.categories) {
                e("  cat: %s", cat!!)
            }
        }
        val bundle = intent.extras
        if (bundle != null) {
            for (key in bundle.keySet()) {
                val value = bundle[key]
                e("  extra: %s->%s", key!!, value!!)
            }
        }
    }

    fun dump(cursor: Cursor?) {
        if (cursor != null && !cursor.isClosed) {
            cursor.moveToFirst()
            do {
                val cc = cursor.columnCount
                e(" cursor: %s/%s", cc, cursor.toString())
                for (i in 0 until cc) {
                    e(
                        "  %s: %s", cursor.getColumnName(i),
                        cursor.getString(i)
                    )
                }
            } while (cursor.moveToNext())
            cursor.moveToFirst()
        }
    }

    fun enter(vararg args: Any?) {
        if (BuildConfig.DEBUG) {
            try {
                val b = StringBuilder()
                for (arg in args) {
                    b.append(arg).append(", ")
                }
                Log.d(TAG, String.format("Enter %s (%s)", methodName, b.toString()))
            } catch (ignored: Throwable) {
            }
        }
    }

    fun debug(vararg args: Any?) {
        if (BuildConfig.DEBUG) {
            try {
                val b = StringBuilder()
                for (arg in args) {
                    b.append(arg).append(", ")
                }
                Log.d(TAG, String.format("Debug %s (%s)", methodName, b.toString()))
            } catch (ignored: Throwable) {
            }
        }
    }

    fun debug_release(vararg args: Any?){
        try {
            val b = StringBuilder()
            for (arg in args) {
                b.append(arg).append(", ")
            }
            println(String.format("Debug Releease %s (%s)", methodName, b.toString()))
        } catch (ignored: Throwable) {
        }
    }

    fun exit(vararg args: Any?) {
        if (BuildConfig.DEBUG) {
            try {
                val b = StringBuilder()
                for (arg in args) {
                    b.append(arg).append(", ")
                }
                Log.d(TAG, String.format("Exit %s (%s)", methodName, b.toString()))
            } catch (ignored: Throwable) {
            }
        }
    }
}