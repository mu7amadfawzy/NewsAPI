package com.tempo.news.di.modules

import com.tempo.news.data.repositories.BaseRepository
import com.tempo.news.data.repositories.ArticlesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [DataSourceModule::class])
abstract class RepositoriesModule {

    @Binds
    @Singleton
    abstract fun provideHomeRepo(articlesRepository: ArticlesRepository): BaseRepository


}