package com.tempo.news.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.tempo.news.R


class ThemeDialogHandler(
    private val context: Context,
    private val callback: ThemePickerCallback? = null,
    private val delegate: AppCompatDelegate
) {
    private val THEME_TAG = "Theme"

    init {
        AppCompatDelegate.setDefaultNightMode(getWhichMode(getSavedTheme()))
    }

    fun showThemesPickerDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.choose_theme_text))
        val styles = arrayOf(
            context.getString(R.string.light_theme),
            context.getString(R.string.dark_theme),
            context.getString(R.string.system_efault_theme)
        )

        builder.setSingleChoiceItems(styles, getSavedTheme()) { dialog, which ->
            onThemeSelected(which)
            dialog?.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun onThemeSelected(which: Int) {
        AppCompatDelegate.setDefaultNightMode(getWhichMode(which))
        delegate.applyDayNight()
        callback?.onThemeSelected(which)
        save(which)
    }

    private fun save(which: Int) {
        getPreferences().edit().putInt(THEME_TAG, which).apply()
    }

    private fun getSavedTheme(): Int {
        return getPreferences().getInt(THEME_TAG, 2)//system default
    }

    private fun getPreferences() =
        context.getSharedPreferences(
            context.getString(R.string.app_name),
            AppCompatActivity.MODE_PRIVATE
        )

    private fun getWhichMode(which: Int): Int {
        return when (which) {
            0 -> AppCompatDelegate.MODE_NIGHT_NO
            1 -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
    }

    interface ThemePickerCallback {
        fun onThemeSelected(which: Int)
    }
}
