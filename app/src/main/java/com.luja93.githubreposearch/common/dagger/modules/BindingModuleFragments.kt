package com.luja93.githubreposearch.common.dagger.modules

import com.luja93.githubreposearch.common.mvvm.BaseFragment
import com.luja93.githubreposearch.githubreposearch.repodetails.RepoDetailsFragment
import com.luja93.githubreposearch.githubreposearch.search.SearchReposFragment
import com.luja93.githubreposearch.githubreposearch.userdetails.UserDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by lleopoldovic on 23/09/2019.
 */

@Module
abstract class BindingModuleFragments {

    @ContributesAndroidInjector
    abstract fun contributeBaseFragment(): BaseFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchReposFragmentFragment(): SearchReposFragment

    @ContributesAndroidInjector
    abstract fun contributeRepoDetailsFragment(): RepoDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeUserDetailsFragment(): UserDetailsFragment
}