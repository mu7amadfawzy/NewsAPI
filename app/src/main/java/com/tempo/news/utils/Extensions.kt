package com.tempo.news.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.annotation.CheckResult
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.google.android.material.snackbar.Snackbar
import com.tempo.news.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

object Extensions {
    /**
     * Extension function to simplify setting an afterTextChanged action to EditText components.
     */
    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    /**
     * Extension function to apply debounce on textChanges using Flow Coroutine
     */
    @ExperimentalCoroutinesApi
    @CheckResult
    fun EditText.textChanges(): Flow<CharSequence?> {
        return callbackFlow<CharSequence?> {
            val listener = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) = Unit
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    trySend(s)
                }
            }
            addTextChangedListener(listener)
            awaitClose { removeTextChangedListener(listener) }
        }.onStart { emit(text) }
    }
    /**
     * Extension function to set the action required on each text change with debounce
     */
    @FlowPreview
    @ExperimentalCoroutinesApi
    fun EditText.configDebounce(
        onEach: (String) -> Any,
        duration: Long = 700,
        scope: CoroutineScope
    ) {
        textChanges().debounce(duration)
            .onEach {
                onEach.invoke(it.toString())
            }
            .launchIn(scope)
    }

    /**
     * Extension function to simplify showing a snackBar using the View.
     */
    fun View.snackError(@StringRes res: Int) {
        snackError(this.context.getString(res))
    }

    fun View.snackError(errorString: String?) {
        errorString?.let { Snackbar.make(this, it, Snackbar.LENGTH_SHORT).show() }
    }

    fun Activity.startActivity(viewToAnimate: View, cls: Class<*>, extras: Bundle) {
        val intent = Intent(this, cls)
        intent.putExtras(extras)
        val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, viewToAnimate, this.getString(R.string.transition)
        )
        ActivityCompat.startActivity(this, intent, options.toBundle())
    }

    fun Activity.startActivity(cls: Class<*>, extras: Bundle, finish: Boolean = false) {
        val intent = Intent(this, cls)
        intent.putExtras(extras)
        this.startActivity(intent)
        if (finish)
            this.finish()
    }
}