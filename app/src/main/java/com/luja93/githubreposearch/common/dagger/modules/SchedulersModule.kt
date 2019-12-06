package com.luja93.githubreposearch.common.dagger.modules

import com.luja93.githubreposearch.common.scheduler.AndroidSchedulerProvider
import com.luja93.githubreposearch.common.scheduler.SchedulerProvider
import dagger.Binds
import dagger.Module

/**
 * Created by lleopoldovic on 23/09/2019.
 */

@Module
abstract class SchedulersModule {

  @Binds
  abstract fun bindSchedulerProvider(androidSchedulerProvider: AndroidSchedulerProvider): SchedulerProvider

}
