package com.luja93.githubreposearch.common.scheduler

import io.reactivex.Scheduler

/**
 * Created by lleopoldovic on 23/09/2019.
 *
 *
 * Allows providing different types of [Scheduler]s.
 */

interface SchedulerProvider {

  fun trampoline(): Scheduler

  fun newThread(): Scheduler

  fun computation(): Scheduler

  fun io(): Scheduler

  fun ui(): Scheduler
}
