package com.luja93.githubreposearch.githubreposearch.repodetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luja93.githubreposearch.R
import com.luja93.githubreposearch.common.mvvm.BaseFragment
import com.luja93.githubreposearch.githubreposearch.model.Repo
import com.luja93.githubreposearch.utils.CustomTabUtils
import com.luja93.githubreposearch.utils.TextViewUtils
import kotlinx.android.synthetic.main.fragment_repo_details.*
import kotlinx.android.synthetic.main.item_details_header.view.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import org.parceler.Parcels

/**
 * Created by lleopoldovic on 07/12/2019.
 */

class RepoDetailsFragment : BaseFragment() {

    companion object {
        private const val ARG_KEY_REPO = "argKeyRepo"

        fun newInstance(repo: Repo) = RepoDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_KEY_REPO, Parcels.wrap(repo))
            }
        }
    }

    private lateinit var repo: Repo
    private var listener: OnRepoDetailsFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            repo = Parcels.unwrap(it.getParcelable(ARG_KEY_REPO))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRepoDetailsFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnRepoDetailsFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun setupUI() {
        appBarLayout.toolbar.title = repo.name

        val title = TextViewUtils.setForegroundSpan(
            appBarLayout.context,
            appBarLayout.context.getString(R.string.made_by_phrase, repo.owner.username),
            R.color.colorAccent, 2
        )
        details_header_view.setHeaderDetails(
            repo.owner.avatarUrl,
            title,
            getString(R.string.created_at, repo.createdAt),
            getString(R.string.updated_at, repo.updatedAt)
        )

        fork_details_DTV.setDetails(getString(R.string.forks_phrase), repo.forksCount.toString())
        watchers_details_DTV.setDetails(
            getString(R.string.watchers_phrase),
            repo.watcherCount.toString()
        )
        issues_details_DTV.setDetails(
            getString(R.string.issues_phrase),
            repo.openIssuesCount.toString()
        )
        language_details_DTV.setDetails(
            getString(R.string.language_phrase), repo.language ?: getString(
                R.string.unknown_phrase
            )
        )
        desc_details_DTV.setDetails(
            getString(R.string.desc_phrase), repo.description ?: getString(
                R.string.no_description_phrase
            )
        )

        details_header_view.avatar_IV.setOnClickListener {
            listener?.onUserClicked(repo.owner.id, repo.owner.username)
        }
        details_header_view.title_TV.setOnClickListener {
            listener?.onUserClicked(repo.owner.id, repo.owner.username)
        }
        show_more_TV.setOnClickListener {
            CustomTabUtils.openInBrowser(desc_details_DTV.context, repo.url)
        }
    }

    interface OnRepoDetailsFragmentInteractionListener {
        fun onUserClicked(id: Long, username: String)
    }
}
