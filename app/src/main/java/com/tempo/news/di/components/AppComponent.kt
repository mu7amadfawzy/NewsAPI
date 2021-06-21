package com.tempo.news.di.components

import android.app.Application
import com.tempo.news.MyApplication
import com.tempo.news.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @Component tells Dagger to generate a container with all the dependencies required
 * to satisfy the types it exposes. This is called a Dagger component;it contains a graph that
 * consists of the objects that Dagger knows how to provide and their respective dependencies.
 * **/
@Singleton
@Component(
    modules = [(AndroidInjectionModule::class), (AndroidSupportInjectionModule::class),
        (AppModule::class), (ActivityBuildersModule::class), (RemoteServiceModule::class),
        (ViewModelModule::class), (FragmentBuilderModule::class), (LocalDataSourceModule::class)]
)
interface AppComponent : AndroidInjector<MyApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}
