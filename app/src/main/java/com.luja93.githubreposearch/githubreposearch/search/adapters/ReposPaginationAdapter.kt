package com.luja93.githubreposearch.githubreposearch.search.adapters

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.luja93.githubreposearch.R
import com.luja93.githubreposearch.common.kotlin.loadUrl
import com.luja93.githubreposearch.common.mvvm.pagination.GenericPagedListAdapter
import com.luja93.githubreposearch.githubreposearch.model.Repo
import com.luja93.githubreposearch.utils.TextUtils
import com.luja93.githubreposearch.utils.TextViewUtils
import kotlinx.android.synthetic.main.item_repo.view.*

/**
 * Created by lleopoldovic on 06/02/2019.
 */

class ReposPaginationAdapter : GenericPagedListAdapter<Repo>(RepoDiffUtilCallback) {

    companion object RepoDiffUtilCallback : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem == newItem
        }
    }

    var listener: OnRepoInteractionListener? = null

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_repo
    }

    override fun getViewHolder(view: View, viewType: Int): GenericViewHolder<Repo> {
        return RepoViewHolder(view)
    }

    inner class RepoViewHolder(itemView: View) : GenericViewHolder<Repo>(itemView) {
        override fun bind(data: Repo) {
            with(itemView) {
                // UI binding
                repo_name_TV.text = data.name
                avatar_IV.loadUrl(data.owner.avatarUrl, false)
                username_TV.text = TextViewUtils.setForegroundSpan(
                    itemView.context,
                    itemView.context.getString(R.string.made_by_phrase, data.owner.username),
                    R.color.colorAccent, 2
                )
                forks_count_TV.text = TextUtils.prettyCount(data.forksCount)
                watchers_count_TV.text = TextUtils.prettyCount(data.watcherCount)
                issues_count_TV.text = TextUtils.prettyCount(data.openIssuesCount)

                // Click listeners
                repo_CV.setOnClickListener { listener?.onRepoClicked(data) }
                avatar_IV.setOnClickListener {
                    listener?.onUserClicked(
                        data.owner.id,
                        data.owner.username
                    )
                }
                username_TV.setOnClickListener {
                    listener?.onUserClicked(
                        data.owner.id,
                        data.owner.username
                    )
                }
            }
        }
    }

    interface OnRepoInteractionListener {
        fun onRepoClicked(repo: Repo)
        fun onUserClicked(id: Long, username: String)
    }
}