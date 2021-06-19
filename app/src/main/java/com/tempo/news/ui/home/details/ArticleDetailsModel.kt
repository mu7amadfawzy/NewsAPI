package com.tempo.news.ui.home.details

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.tempo.news.BR
import com.tempo.news.data.model.ArticleDM


class ArticleDetailsModel : BaseObservable() {

    var requestError: String? = null

    @get:Bindable
    var loading: Boolean? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    @get:Bindable
    var dataModel: ArticleDM? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.dataModel)
        }
}