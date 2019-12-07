package com.luja93.githubreposearch.githubreposearch.search.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.luja93.githubreposearch.R
import com.luja93.githubreposearch.common.kotlin.inflate
import com.luja93.githubreposearch.common.kotlin.loadUrl
import com.luja93.githubreposearch.githubreposearch.model.Repo
import com.luja93.githubreposearch.utils.TextViewUtils
import kotlinx.android.synthetic.main.item_repo.view.*

/**
 * Created by lleopoldovic on 06/12/2019.
 */

class ReposAdapter : ListAdapter<Repo, ReposAdapter.RepoViewHolder>(RepoDiffUtilCallbackImpl()) {

    var listener: OnRepoInteractionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(parent.inflate(R.layout.item_repo))


    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repo: Repo) {
            with(itemView) {
                // UI binding
                repo_name_TV.text = repo.name
                avatar_IV.loadUrl(repo.owner.avatarUrl, false)
                username_TV.text = TextViewUtils.setForegroundSpan(
                    itemView.context,
                    itemView.context.getString(R.string.made_by_phrase, repo.owner.username),
                    R.color.colorAccent, 2
                )
                forks_count_TV.text = repo.forksCount.toString()
                watchers_count_TV.text = repo.watcherCount.toString()
                issues_count_TV.text = repo.openIssuesCount.toString()

                // Click listeners
                repo_CV.setOnClickListener { listener?.onRepoClicked(repo) }
                avatar_IV.setOnClickListener {
                    listener?.onUserClicked(
                        repo.owner.id,
                        repo.owner.username
                    )
                }
                username_TV.setOnClickListener {
                    listener?.onUserClicked(
                        repo.owner.id,
                        repo.owner.username
                    )
                }
            }
        }
    }

    class RepoDiffUtilCallbackImpl : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem == newItem
        }
    }

    interface OnRepoInteractionListener {
        fun onRepoClicked(repo: Repo)
        fun onUserClicked(id: Long, username: String)
    }
}