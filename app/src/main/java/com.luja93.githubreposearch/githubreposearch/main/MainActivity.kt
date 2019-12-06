package com.luja93.githubreposearch.githubreposearch.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.luja93.githubreposearch.R
import com.luja93.githubreposearch.common.mvvm.BaseActivity
import com.luja93.githubreposearch.githubreposearch.search.SearchReposFragment

class MainActivity : BaseActivity() {

    private val searchFragment: SearchReposFragment by lazy { SearchReposFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment(searchFragment)
    }

    private fun setFragment(fragment: Fragment) {
        setFragment(fragment, true)
    }

    private fun setFragment(fragment: Fragment, animate: Boolean) {
        supportFragmentManager
            .beginTransaction()
            .setTransition(if (animate) FragmentTransaction.TRANSIT_FRAGMENT_FADE else FragmentTransaction.TRANSIT_NONE)
            .replace(R.id.container, fragment, fragment.javaClass.name)
            .commit()
    }
}
