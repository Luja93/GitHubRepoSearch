package com.luja93.githubreposearch.githubreposearch.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding.widget.RxTextView
import com.luja93.githubreposearch.R
import com.luja93.githubreposearch.common.kotlin.visibleIf
import com.luja93.githubreposearch.common.mvvm.BaseFragment
import com.luja93.githubreposearch.common.mvvm.ResourceStateObserver
import com.luja93.githubreposearch.githubreposearch.AppConstants.DEBOUNCE_TIME_MILLIS
import com.luja93.githubreposearch.githubreposearch.model.Repo
import com.luja93.githubreposearch.githubreposearch.search.adapters.ReposAdapter
import com.luja93.githubreposearch.utils.view.VerticalOffsetDecoration
import kotlinx.android.synthetic.main.fragment_search_repos.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * Created by lleopoldovic on 06/12/2019.
 */

class SearchReposFragment : BaseFragment(), ReposAdapter.OnRepoInteractionListener {

    companion object {
        const val TAG = "SearchReposFragment"

        fun newInstance() = SearchReposFragment()
    }

    private val viewModel: SearchReposViewModel by viewModels { viewModelFactory }
    private val reposAdapter: ReposAdapter = ReposAdapter()
    private var listener: OnSearchReposFragmentInteractionListener? = null

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSearchReposFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnSearchReposFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun setupUI() {
        // TODO: Extract this string
        appBarLayout.toolbar.title = "GitHub Repo Searcher"

        repos_RV.layoutManager = LinearLayoutManager(repos_RV.context)
        repos_RV.isMotionEventSplittingEnabled = false
        repos_RV.addItemDecoration(VerticalOffsetDecoration(repos_RV.context, 16f))
        repos_RV.adapter = reposAdapter
        reposAdapter.listener = this

        RxTextView.textChanges(search_repos_ET)
            .debounce(DEBOUNCE_TIME_MILLIS, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<CharSequence>() {
                override fun onCompleted() {}
                override fun onError(e: Throwable) {}
                override fun onNext(charSequence: CharSequence) {
                    viewModel.searchRepositories(charSequence.toString().trim())
                }
            })
    }

    private fun bindUI() {
        viewModel.repositories.observe(viewLifecycleOwner, ResourceStateObserver(this, {
            it?.let {
                reposAdapter.submitList(it)
            }
        }, onLoading = { appBarLayout.toolbarProgressCircle.visibleIf(it) }))
    }


    // Adapter listeners
    override fun onRepoClicked(repo: Repo) {
        listener?.onRepoClicked(repo)
    }

    override fun onUserClicked(id: Long, username: String) {
        listener?.onUserClicked(id, username)
    }


    interface OnSearchReposFragmentInteractionListener {
        fun onRepoClicked(repo: Repo)
        fun onUserClicked(id: Long, username: String)
    }
}
