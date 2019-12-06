package com.luja93.githubreposearch.common.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.luja93.githubreposearch.githubreposearch.example.ExampleViewModel
import com.luja93.githubreposearch.common.dagger.qualifiers.ViewModelKey
import com.luja93.githubreposearch.common.mvvm.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by lleopoldovic on 16/09/2019.
 */

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ExampleViewModel::class)
    abstract fun bindIntroViewModel(introViewModel: ExampleViewModel): ViewModel

}