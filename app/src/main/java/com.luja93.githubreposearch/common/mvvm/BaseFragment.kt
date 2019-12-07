package com.luja93.githubreposearch.common.mvvm

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by lleopoldovic on 16/09/2019.
 */

abstract class BaseFragment : Fragment(), BaseView, HasAndroidInjector {

    // DI:
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    // UI vars
    // Added a check, tests don't pass if there is no check(EmptyFragmentActivity cant be cast to BaseActivity)
    val baseActivity: BaseActivity? by lazy {
        if (activity is BaseActivity) {
            activity as BaseActivity
        } else {
            null
        }
    }


    /*
        Lifecycle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        // Fragments use AndroidSupportInjection
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////


    /*
        UI funcs:
     */
    override fun showError(errorMessage: String) {
        baseActivity?.showShortInfo(errorMessage)
    }

    override fun showError(stringResourceId: Int) {
        baseActivity?.showError(stringResourceId)
    }

    override fun showShortInfo(info: String) {
        baseActivity?.showShortInfo(info)
    }

    override fun showShortInfo(stringResourceId: Int) {
        baseActivity?.showShortInfo(stringResourceId)
    }

    override fun showProgressCircle(show: Boolean) {
        baseActivity?.showProgressCircle(show)
    }

    override fun showLoader(show: Boolean) {
        baseActivity?.showLoader(show)
    }

    override fun showLoader(show: Boolean, cancelable: Boolean) {
        baseActivity?.showLoader(show, cancelable)
    }

    override fun hideKeyboard() {
        baseActivity?.hideKeyboard()
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

}