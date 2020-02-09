package com.luja93.githubreposearch.githubreposearch.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.luja93.githubreposearch.R
import com.luja93.githubreposearch.common.kotlin.gone
import com.luja93.githubreposearch.common.kotlin.visible
import com.luja93.githubreposearch.common.mvvm.BaseFragment
import com.luja93.githubreposearch.common.mvvm.ResourceStateObserver
import com.luja93.githubreposearch.githubreposearch.model.User
import com.luja93.githubreposearch.utils.CustomTabUtils
import com.luja93.githubreposearch.utils.TextViewUtils
import kotlinx.android.synthetic.main.fragment_user_details.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*

/**
 * Created by lleopoldovic on 07/12/2019.
 */

class UserDetailsFragment : BaseFragment() {

    companion object {
        private const val ARG_USER_ID_KEY = "id"
        private const val ARG_USERNAME_KEY = "username"
    }

    private val viewModel: UserDetailsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        bindUI()

        arguments?.let { bundle ->
            viewModel.fetchUser(
                bundle.getLong(ARG_USER_ID_KEY),
                bundle.getString(ARG_USERNAME_KEY) ?: ""
            )
        }
    }

    private fun setupUI() {
        details_group.gone()
    }

    private fun bindUI() {
        viewModel.user.observe(viewLifecycleOwner, ResourceStateObserver(this, {
            it?.let { user -> setUserDetails(user) }
        }))
    }

    private fun setUserDetails(user: User) {
        appBarLayout.toolbar.title = user.name

        details_group.visible()
        warning_group.gone()

        val title = TextViewUtils.setForegroundSpan(
            details_header_view.context,
            user.name ?: getString(R.string.not_defined_phrase),
            R.color.colorAccent, 0
        )
        details_header_view.setHeaderDetails(
            user.avatarUrl, title,
            getString(R.string.created_at, user.createdAt),
            getString(R.string.updated_at, user.updatedAt)
        )

        // Details card data
        type_details_DTV.setDetails(getString(R.string.type_phrase), user.type)
        email_details_DTV.setDetails(
            getString(R.string.email_phrase),
            user.email ?: getString(R.string.unavailable_phrase)
        )
        location_details_DTV.setDetails(
            getString(R.string.location_phrase),
            user.location ?: getString(R.string.unavailable_phrase)
        )
        public_repos_details_DTV.setDetails(
            getString(R.string.public_repos_phrase),
            user.publicReposCount.toString()
        )
        public_gists_details_DTV.setDetails(
            getString(R.string.public_gists_phrase),
            user.publicGistsCount.toString()
        )
        followers_details_DTV.setDetails(
            getString(R.string.followers_phrase),
            user.followersCount.toString()
        )
        following_details_DTV.setDetails(
            getString(R.string.following_phrase),
            user.followingCount.toString()
        )
        bio_details_DTV.setDetails(
            getString(R.string.bio_phrase),
            user.bio ?: getString(R.string.not_defined_phrase)
        )

        // Click listeners
        show_more_TV.setOnClickListener {
            CustomTabUtils.openInBrowser(show_more_TV.context, user.url)
        }
    }
}
