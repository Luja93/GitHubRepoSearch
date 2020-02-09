package com.luja93.githubreposearch.githubreposearch.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import com.luja93.githubreposearch.R
import com.luja93.githubreposearch.common.kotlin.visibleIf
import com.luja93.githubreposearch.common.mvvm.BaseFragment
import com.luja93.githubreposearch.common.mvvm.ResourceStateObserver
import com.luja93.githubreposearch.githubreposearch.AppConstants.BLANK_SEARCH
import com.luja93.githubreposearch.githubreposearch.AppConstants.DEBOUNCE_TIME_MILLIS
import com.luja93.githubreposearch.githubreposearch.model.Repo
import com.luja93.githubreposearch.githubreposearch.repodetails.RepoDetailsFragment
import com.luja93.githubreposearch.githubreposearch.search.adapters.ReposPaginationAdapter
import com.luja93.githubreposearch.utils.view.VerticalOffsetDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_search_repos.*
import kotlinx.android.synthetic.main.fragment_search_repos.view.*
import java.util.concurrent.TimeUnit

/**
 * Created by lleopoldovic on 06/12/2019.
 */

class SearchReposFragment : BaseFragment(), ReposPaginationAdapter.OnRepoInteractionListener {

    private val viewModel: SearchReposViewModel by viewModels { viewModelFactory }
    private val reposAdapter: ReposPaginationAdapter = ReposPaginationAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_repos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        bindUI()
    }

    private fun setupUI() {
        repos_RV.layoutManager = LinearLayoutManager(repos_RV.context)
        repos_RV.isMotionEventSplittingEnabled = false
        repos_RV.addItemDecoration(VerticalOffsetDecoration(repos_RV.context, 16f))
        repos_RV.adapter = reposAdapter
        reposAdapter.listener = this

        @Suppress("unused")
        search_view.queryTextChanges()
            .debounce(DEBOUNCE_TIME_MILLIS, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.searchRepositories(it.toString())
            }

        // TODO
//        sort_btn.setOnClickListener {
//            showSortingMenu(it)
//        }
    }

    private fun bindUI() {
        viewModel.totalCount.observe(viewLifecycleOwner, Observer {
            setPlaceholder(it)
            setContentVisibility(it)
        })
        viewModel.query.observe(viewLifecycleOwner, Observer {
            if (search_view.query.toString() != it)
                search_view.setQuery(it, false)
        })
        viewModel.sorting.observe(viewLifecycleOwner, Observer {
            // TODO: Set sorting button in the proper way
            // sort_btn.text = it.name
        })
        viewModel.repos.observe(
            viewLifecycleOwner, ResourceStateObserver(this,
                onSuccess = { repos -> repos?.let { setResults(repos) } },
                onLoading = { app_bar_layout.toolbar_progress_circle.visibleIf(it) }
            )
        )
    }

    private fun setResults(repos: PagedList<Repo>) {
        reposAdapter.submitList(repos)
    }

    private fun setPlaceholder(totalCount: Long) {
        when (totalCount) {
            BLANK_SEARCH -> instructions_TV.text = getString(R.string.instructions_phrase)
            0L -> instructions_TV.text = getString(R.string.no_results_phrase)
        }
    }

    private fun setContentVisibility(totalCount: Long) {
        if (totalCount <= 0) {
            expandToolbar()
        }
        repos_RV.visibleIf(totalCount > 0)
        instructions_group.visibleIf(totalCount <= 0)
    }

    private fun expandToolbar() {
        app_bar_layout.setExpanded(true, true)
    }

    private fun showSortingMenu(view: View) {
        val menu = PopupMenu(context, view)

        Repo.Sorting.values().forEachIndexed { index, sorting ->
            menu.menu.add(1, index, index, sorting.name)
        }

        menu.setOnMenuItemClickListener {
            viewModel.setSortingOption(it.itemId)
            viewModel.searchRepositories(search_view.query.toString(), true)
            true
        }

        menu.show()
    }

    // Adapter listeners
    override fun onRepoClicked(repo: Repo) {
        findNavController().navigate(
            R.id.action_search_repos_to_repo_details,
            RepoDetailsFragment.newBundle(repo)
        )
    }

    override fun onUserClicked(id: Long, username: String) {
        findNavController().navigate(
            SearchReposFragmentDirections.actionSearchReposToUserDetails(
                id, username
            )
        )
    }
}
