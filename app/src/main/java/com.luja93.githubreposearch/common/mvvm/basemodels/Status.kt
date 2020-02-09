package com.luja93.githubreposearch.common.mvvm.basemodels

/**
 * Created by lleopoldovic on 23/09/2019.
 *
 *
 * Status of a resource that is provided to the view (activity/fragment).
 *
 * Note: IDLE is usually used only for data initialization and doesn't have a dedicated UI state
 *       within the [ResourceStateObserver].
 */

enum class Status {
  SUCCESS,
  ERROR,
    LOADING,
    IDLE
}
