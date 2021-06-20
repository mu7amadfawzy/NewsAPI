package com.tempo.news.di.modules

import com.tempo.news.ui.home.HomeActivity
import com.tempo.news.ui.home.details.ArticleDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * ActivityBuildersModule which is responsible for injecting activities
 * and creating subcomponents for them.
 * */

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributeArticleDetailsActivity(): ArticleDetailsActivity

}