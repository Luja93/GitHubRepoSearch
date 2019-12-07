package com.luja93.githubreposearch.common.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.luja93.githubreposearch.common.dagger.qualifiers.ViewModelKey
import com.luja93.githubreposearch.common.mvvm.ViewModelFactory
import com.luja93.githubreposearch.githubreposearch.search.SearchReposViewModel
import com.luja93.githubreposearch.githubreposearch.userdetails.UserDetailsViewModel
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
    @ViewModelKey(SearchReposViewModel::class)
    abstract fun bindSearchReposViewModel(searchReposViewModel: SearchReposViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailsViewModel::class)
    abstract fun bindUserDetailsViewModel(userDetailsViewModel: UserDetailsViewModel): ViewModel

}