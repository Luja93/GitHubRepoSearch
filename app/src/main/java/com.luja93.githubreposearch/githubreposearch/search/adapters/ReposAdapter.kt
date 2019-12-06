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
import kotlinx.android.synthetic.main.item_repo.view.*

class ReposAdapter : ListAdapter<Repo, ReposAdapter.RepoViewHolder>(RepoDiffUtilCallbackImpl()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(parent.inflate(R.layout.item_repo))


    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repo: Repo) {
            with(itemView) {
                repo_name_TV.text = repo.name
                username_TV.text = repo.owner.username
                avatar_IV.loadUrl(repo.owner.avatarUrl)
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
}