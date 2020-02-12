package com.luja93.githubreposearch.common.mvvm.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by lleopoldovic on 27/01/2020.
 */

abstract class GenericPagedListAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, GenericPagedListAdapter.GenericViewHolder<T>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        return getViewHolder(
            LayoutInflater.from(parent.context).inflate(getLayoutId(viewType), parent, false),
            viewType
        )
    }

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        getItem(position)?.let { data -> holder.bind(data) }
    }

    abstract class GenericViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(data: T)
    }

    protected abstract fun getLayoutId(viewType: Int): Int

    protected abstract fun getViewHolder(view: View, viewType: Int): GenericViewHolder<T>
}