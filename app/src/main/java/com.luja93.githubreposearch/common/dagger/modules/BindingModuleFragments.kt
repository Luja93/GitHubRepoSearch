package com.luja93.githubreposearch.common.dagger.modules

import com.luja93.githubreposearch.githubreposearch.example.ExampleFragment
import com.luja93.githubreposearch.common.mvvm.BaseFragment
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
    abstract fun contributeExampleFragment(): ExampleFragment
}