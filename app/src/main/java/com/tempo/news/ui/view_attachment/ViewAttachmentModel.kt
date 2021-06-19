package com.tempo.news.ui.view_attachment

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

/**
 * Created by Fawzy on 24,March,2019.
 * ma7madfawzy@gmail.com
 */
class ViewAttachmentModel : BaseObservable() {

    @get:Bindable
    var isShowImage = true
        set(value) {
            isShowImage = value
            notifyPropertyChanged(BR.showImage)
        }

    @get:Bindable
    var imageUrl: String? = null
        set(value) {
            imageUrl = value
            notifyPropertyChanged(BR.imageUrl)
        }
}