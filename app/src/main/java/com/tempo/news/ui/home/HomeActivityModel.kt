package com.tempo.news.ui.home

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.tempo.news.BR

/**
 * Data validation state of the add contact form.
 */

class HomeActivityModel : BaseObservable() {
    fun onSuccess() {
        messageText = null
    }

    var queryText: String? = ""
    var showToast: Boolean? = false

    @get:Bindable
    var messageText: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.messageText)
        }

    @get:Bindable
    var loading: Boolean? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

}