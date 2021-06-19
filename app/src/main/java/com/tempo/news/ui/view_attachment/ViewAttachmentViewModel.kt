package com.tempo.news.ui.view_attachment

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewAttachmentViewModel : ViewModel() {

    val model = ViewAttachmentModel()

    fun readExtras(extras: Bundle?) {
        viewModelScope.launch(Dispatchers.IO) {
            model.imageUrl = fetchExtras(extras)
        }
    }

    private suspend fun fetchExtras(extras: Bundle?): String? = extras?.getString("filePath")


}