package com.simpnic.locale

import android.content.res.Resources
import android.icu.util.MeasureUnit.CELSIUS
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.os.ConfigurationCompat

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Locale List
        val localeListCompat = ConfigurationCompat.getLocales(Resources.getSystem().configuration)
        Log.d(TAG, localeListCompat.toString())
        // select index 0 is default locale (language + country)
        val locale = localeListCompat.get(0)
        Log.d(TAG, locale.toString())
        val language = locale.language
        val country = locale.country
        Log.d(TAG, " locale: language=$language country=$country")
        if (null == SharedPreferencesManager.getInstance(this).language) {
            when (language) {
                "zh" -> SharedPreferencesManager.getInstance(this).updateLanguage(LanguageUtils.ZHTW)
                "jp" -> SharedPreferencesManager.getInstance(this).updateLanguage(LanguageUtils.JP)
                else -> SharedPreferencesManager.getInstance(this).updateLanguage(LanguageUtils.EN)
            }
        }

        if (null == SharedPreferencesManager.getInstance(this).temperatureUnit) {
            SharedPreferencesManager.getInstance(this).updateTemperatureUnit(LanguageUtils.CELSIUS)
        }
    }
}
