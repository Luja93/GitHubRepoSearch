package com.luja93.githubreposearch.common.mvvm

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.greysonparrelli.permiso.Permiso
import com.luja93.githubreposearch.R
import com.luja93.githubreposearch.common.kotlin.visibleIf
import com.luja93.githubreposearch.common.session.SessionPrefImpl
import com.luja93.githubreposearch.utils.DialogUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Created by lleopoldovic on 16/09/2019.
 */

abstract class BaseActivity : AppCompatActivity(), BaseView, HasAndroidInjector {

    // DI:
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    @Inject
    lateinit var session: SessionPrefImpl

    // UI vars:
    private lateinit var pd: Dialog

    private var toolbar: Toolbar? = null
    private var toolbarProgressCircle: ProgressBar? = null


    /*
        Lifecycle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        Permiso.getInstance().setActivity(this)

        pd = DialogUtil.buildLoaderDialog(this, getString(R.string.please_wait))
    }

    override fun onResume() {
        super.onResume()
        Permiso.getInstance().setActivity(this)
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    /*
        UI funcs:
     */
    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)

        toolbar = findViewById(R.id.toolbar)
        toolbarProgressCircle = findViewById(R.id.toolbarProgressCircle)

    }

    override fun showError(errorMessage: String) {
        showShortInfo(errorMessage)
    }
    override fun showError(stringResourceId: Int) {
        showError(getString(stringResourceId))
    }

    override fun showShortInfo(info: String) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show()
    }

    override fun showShortInfo(stringResourceId: Int) {
        showShortInfo(getString(stringResourceId))
    }

    override fun showProgressCircle(show: Boolean) {
        toolbarProgressCircle?.visibleIf(show) ?: showLoader(show)
    }

    override fun showLoader(show: Boolean) {
        if (show) {
            pd.show()
        } else {
            pd.dismiss()
        }

        if (!show) {
            pd.setCancelable(true)
        }
    }

    override fun showLoader(show: Boolean, cancelable: Boolean) {
        pd.setCancelable(cancelable)
        showLoader(show)
    }

    override fun hideKeyboard() {
        val view = window.decorView
        if (view != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Permiso.getInstance().setActivity(this)
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
}