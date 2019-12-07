package com.luja93.githubreposearch.githubreposearch.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.luja93.githubreposearch.R
import com.luja93.githubreposearch.common.mvvm.BaseActivity
import com.luja93.githubreposearch.githubreposearch.model.Repo
import com.luja93.githubreposearch.githubreposearch.repodetails.RepoDetailsFragment
import com.luja93.githubreposearch.githubreposearch.search.SearchReposFragment

/**
 * Created by lleopoldovic on 06/12/2019.
 */

class MainActivity : BaseActivity(),
    SearchReposFragment.OnSearchReposFragmentInteractionListener,
    RepoDetailsFragment.OnRepoDetailsFragmentInteractionListener {

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
            .addToBackStack(fragment.tag)
            .setTransition(if (animate) FragmentTransaction.TRANSIT_FRAGMENT_OPEN else FragmentTransaction.TRANSIT_NONE)
            .replace(R.id.container, fragment, fragment.javaClass.name)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun onRepoClicked(repo: Repo) {
        setFragment(RepoDetailsFragment.newInstance(repo))
    }

    override fun onUserClicked(username: String) {
        // TODO: Set user details fragment
    }
}
