package com.luja93.githubreposearch.common.mvvm

/**
 * Created by lleopoldovic on 16/09/2019.
 */

interface BaseView {

    fun showProgressCircle(show: Boolean)

    fun showError(errorMessage: String)

    fun showError(stringResourceId: Int)

    fun showLoader(show: Boolean)

    fun showLoader(show: Boolean, cancelable: Boolean)

    fun showShortInfo(info: String)

    fun showShortInfo(stringResourceId: Int)

    fun hideKeyboard()

}