package com.luja93.githubreposearch.githubreposearch.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.luja93.githubreposearch.R
import com.luja93.githubreposearch.common.mvvm.BaseActivity
import com.luja93.githubreposearch.githubreposearch.model.Repo
import com.luja93.githubreposearch.githubreposearch.repodetails.RepoDetailsFragment
import com.luja93.githubreposearch.githubreposearch.search.SearchReposFragment
import com.luja93.githubreposearch.githubreposearch.userdetails.UserDetailsFragment

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
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(fragment.tag)
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

    override fun onUserClicked(id: Long, username: String) {
        setFragment(UserDetailsFragment.newInstance(id, username))
    }
}
