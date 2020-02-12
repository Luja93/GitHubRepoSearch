package com.luja93.githubreposearch.githubreposearch.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import com.luja93.githubreposearch.R
import com.luja93.githubreposearch.common.kotlin.invisible
import com.luja93.githubreposearch.common.kotlin.visible
import com.luja93.githubreposearch.common.kotlin.visibleIf
import com.luja93.githubreposearch.common.mvvm.BaseFragment
import com.luja93.githubreposearch.common.mvvm.ResourceStateObserver
import com.luja93.githubreposearch.githubreposearch.AppConstants.BLANK_SEARCH
import com.luja93.githubreposearch.githubreposearch.AppConstants.DEBOUNCE_TIME_MILLIS
import com.luja93.githubreposearch.githubreposearch.model.Repo
import com.luja93.githubreposearch.githubreposearch.repodetails.RepoDetailsFragment
import com.luja93.githubreposearch.githubreposearch.search.adapters.ReposPaginationAdapter
import com.luja93.githubreposearch.utils.DialogUtil
import com.luja93.githubreposearch.utils.view.VerticalOffsetDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_search_repos.*
import kotlinx.android.synthetic.main.fragment_search_repos.view.*
import java.util.concurrent.TimeUnit

/**
 * Created by lleopoldovic on 06/12/2019.
 */

class SearchReposFragment : BaseFragment(), ReposPaginationAdapter.OnRepoInteractionListener {

    private lateinit var bottomSheetDialog: BottomSheetDialog

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

        sorting_btn.setOnClickListener {
            bottomSheetDialog.show()
        }
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
            setupSortingUI(it)
        })
        viewModel.repos.observe(
            viewLifecycleOwner, ResourceStateObserver(this,
                onSuccess = { repos -> repos?.let { setResults(repos) } },
                onLoading = { isLoading -> activateProgressBar(isLoading) }
            )
        )
    }

    private fun setupSortingUI(sorting: Repo.Sorting) {
        activity?.let {
            bottomSheetDialog = DialogUtil.buildSortingBottomSheetDialog(it, sorting) { sorting ->
                viewModel.setSorting(sorting)
            }
        }
    }

    private fun activateProgressBar(isLoading: Boolean) {
        if (isLoading) {
            app_bar_layout.progress_bar.visible()
            app_bar_layout.progress_bar_placeholder.invisible()
        } else {
            app_bar_layout.progress_bar.invisible()
            app_bar_layout.progress_bar_placeholder.visible()
        }
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
            repos_RV.invisible()
        } else {
            repos_RV.visible()
        }
        instructions_group.visibleIf(totalCount <= 0)
    }

    private fun expandToolbar() {
        app_bar_layout.setExpanded(true, true)
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
