package com.luja93.githubreposearch.common.dagger.modules

import com.luja93.githubreposearch.githubreposearch.main.MainActivity
import com.luja93.githubreposearch.common.mvvm.BaseActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by lleopoldovic on 23/09/2019.
 */

@Module
abstract class BindingModuleActivities {

    @ContributesAndroidInjector
    abstract fun contributeBaseActivity(): BaseActivity

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}