package com.tempo.news.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tempo.news.di.annotations.ViewModelKey
import com.tempo.news.ui.home.HomeActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
/**
 * @IntoMap annotation says that Provider object for this service will be inserted into Map,
 * and @ViewModelKey annotation specifies under which key it will reside.
 * */
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeActivityViewModel::class)
    internal abstract fun bindHomeActivityViewModel(viewModel: HomeActivityViewModel): ViewModel

}
