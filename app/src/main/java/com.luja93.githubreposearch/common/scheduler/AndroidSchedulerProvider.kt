package com.luja93.githubreposearch.common.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by lleopoldovic on 23/09/2019.
 *
 * Provides different types of schedulers specific to Android framework.
 */

class AndroidSchedulerProvider @Inject constructor() : SchedulerProvider {

  override fun trampoline(): Scheduler {
    return Schedulers.trampoline()
  }

  override fun newThread(): Scheduler {
    return Schedulers.newThread()
  }

  override fun computation(): Scheduler {
    return Schedulers.computation()
  }

  override fun io(): Scheduler {
    return Schedulers.io()
  }

  override fun ui(): Scheduler {
    return AndroidSchedulers.mainThread()
  }

}

// Provider used in testing
class TestSchedulerProvider @Inject constructor() : SchedulerProvider {
  override fun trampoline(): Scheduler {
    return Schedulers.trampoline()
  }

  override fun newThread(): Scheduler {
    return Schedulers.trampoline()
  }

  override fun computation(): Scheduler {
    return Schedulers.trampoline()
  }

  override fun io(): Scheduler {
    return Schedulers.trampoline()
  }

  override fun ui(): Scheduler {
    return Schedulers.trampoline()
  }
}