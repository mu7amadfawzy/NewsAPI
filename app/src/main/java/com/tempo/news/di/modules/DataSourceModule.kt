package com.tempo.news.di.modules

import dagger.Module

@Module(includes = [RemoteServiceModule::class])
abstract class DataSourceModule {

//    @Binds
//    @Singleton
//    abstract fun provideHomeDataSource(newsDataSource: NewsDataSource): NewsDataSource


}