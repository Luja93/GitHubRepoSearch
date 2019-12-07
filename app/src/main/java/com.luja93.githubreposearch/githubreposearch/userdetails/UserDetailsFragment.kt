package com.luja93.githubreposearch.githubreposearch.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.luja93.githubreposearch.R
import com.luja93.githubreposearch.common.mvvm.BaseFragment
import com.luja93.githubreposearch.common.mvvm.ResourceStateObserver
import com.luja93.githubreposearch.githubreposearch.model.User
import com.luja93.githubreposearch.utils.CustomTabUtils
import com.luja93.githubreposearch.utils.TextViewUtils
import kotlinx.android.synthetic.main.fragment_user_details.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*

class UserDetailsFragment : BaseFragment() {

    companion object {
        private const val ARG_USER_ID_KEY = "argUserIdKey"
        private const val ARG_USERNAME_KEY = "argUsernameKey"

        @JvmStatic
        fun newInstance(id: Long, username: String) =
            UserDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_USER_ID_KEY, id)
                    putString(ARG_USERNAME_KEY, username)
                }
            }
    }

    private val viewModel: UserDetailsViewModel by viewModels { viewModelFactory }
    private var id: Long = 0
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            id = it.getLong(ARG_USER_ID_KEY)
            username = it.getString(ARG_USERNAME_KEY) ?: ""
        }
    }

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

        viewModel.fetchUser(id, username)
    }

    private fun setupUI() {
        appBarLayout.toolbar.title = username
    }

    private fun bindUI() {
        // TODO: Handle errors!
        // TODO: Add placeholder if user is not found or if error ocurred!
        viewModel.user.observe(viewLifecycleOwner, ResourceStateObserver(this, {
            it?.let { user -> setUserDetails(user) }
        }))
    }

    private fun setUserDetails(user: User) {
        val title = TextViewUtils.setForegroundSpan(
            details_header_view.context,
            user.name,
            R.color.colorAccent, 0
        )
        details_header_view.setHeaderDetails(
            user.avatarUrl, title,
            getString(R.string.created_at, user.createdAt),
            getString(R.string.updated_at, user.updatedAt)
        )

        type_details_DTV.setDetails(getString(R.string.type_phrase), user.type)
        email_details_DTV.setDetails(
            getString(R.string.email_phrase),
            user.email ?: getString(R.string.unavailable_phrase)
        )
        location_details_DTV.setDetails(
            getString(R.string.location_phrase),
            user.email ?: getString(R.string.unavailable_phrase)
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
        following_details_DTV.setDetails(
            getString(R.string.following_phrase),
            user.followingCount.toString()
        )
        bio_details_DTV.setDetails(
            getString(R.string.bio_phrase),
            user.bio ?: getString(R.string.not_defined_phrase)
        )

        show_more_TV.setOnClickListener {
            CustomTabUtils.openInBrowser(show_more_TV.context, user.url)
        }
    }
}
