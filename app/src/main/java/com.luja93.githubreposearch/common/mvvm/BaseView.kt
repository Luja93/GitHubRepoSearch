package com.luja93.githubreposearch.common.mvvm

import androidx.annotation.StringRes

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

    fun showInfoDialog(title: String?, description: String?, buttonText: String?)

    fun showInfoDialog(@StringRes titleResourceId: Int, @StringRes descriptionResourceId: Int, @StringRes buttonText: Int)

    fun hideKeyboard()

    fun onLogout()

}