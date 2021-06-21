package com.tempo.news.di.modules

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.tempo.news.R
import dagger.Module
import dagger.Provides

/**
 * ActivityBuildersModule which is responsible for injecting activities
 * and creating subcomponents for them.
 * */
@Module
class LocalDataSourceModule {

    @Provides
    fun providePrefrences(context: Context) = context.getSharedPreferences(
        context.getString(R.string.app_name),
        AppCompatActivity.MODE_PRIVATE
    )
}