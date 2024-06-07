package nam.tran.data.preference

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class StorePreferenceImpl @Inject constructor(private val app : Application) : StorePreference {
    var pref : SharedPreferences = app.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)

    companion object{
        private const val KEY_PREFERENCES: String = "key_preference"
    }
}