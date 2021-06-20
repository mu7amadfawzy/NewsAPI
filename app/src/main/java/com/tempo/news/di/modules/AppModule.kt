package com.tempo.news.di.modules

import android.app.Application
import android.content.Context
import com.tempo.news.ui.home.HomeActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

/**
 * ActivityBuildersModule which is responsible for injecting activities
 * and creating subcomponents for them.
 * */
@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context

}