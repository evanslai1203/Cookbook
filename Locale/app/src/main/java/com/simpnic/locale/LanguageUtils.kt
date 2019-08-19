package com.simpnic.locale

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.text.TextUtils
import android.util.DisplayMetrics

import java.util.Locale

object LanguageUtils {
    val ZHTW = "zh-TW"
    val EN = "en"
    val JP = "ja"
    val CELSIUS = "°C"

    fun resetDefaultLanguage(context: Context) {

        val resources = context.resources
        val dm = resources.displayMetrics
        val config = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(getSetLocale(context))
        }
        resources.updateConfiguration(config, dm)
    }

    fun getSetLocale(context: Context): Locale {
        return when(SharedPreferencesManager.getInstance(context).language) {
            ZHTW -> Locale.TAIWAN
            JP -> Locale.JAPANESE
            else -> Locale.ENGLISH
        }
    }

    fun getLanguageSelectIndex(context: Context): Int {
        return when(SharedPreferencesManager.getInstance(context).language) {
            JP -> 0
            EN -> 1
            else -> 2
        }
    }

}
