package com.luja93.githubreposearch.githubreposearch.repodetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
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
        private const val ARG_KEY_REPO = "repo"

        /**
         * Use this method to pass a bundle with action using a navigation graph.
         */
        fun newBundle(repo: Repo) = bundleOf(ARG_KEY_REPO to Parcels.wrap(repo))
    }

    private lateinit var repo: Repo

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

        // Details card setup
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

        // Click listeners
        details_header_view.avatar_IV.setOnClickListener {
            findNavController().navigate(
                RepoDetailsFragmentDirections.actionRepoDetailsToUserDetails(
                    repo.owner.id, repo.owner.username
                )
            )
        }
        details_header_view.title_TV.setOnClickListener {
            findNavController().navigate(
                RepoDetailsFragmentDirections.actionRepoDetailsToUserDetails(
                    repo.owner.id, repo.owner.username
                )
            )
        }
        show_more_TV.setOnClickListener {
            CustomTabUtils.openInBrowser(desc_details_DTV.context, repo.url)
        }
    }
}
