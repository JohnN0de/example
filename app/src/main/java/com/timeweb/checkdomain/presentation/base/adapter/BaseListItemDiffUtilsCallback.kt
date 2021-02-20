package com.timeweb.checkdomain.presentation.base.adapter

import androidx.recyclerview.widget.DiffUtil

class BaseListItemDiffUtilsCallback<T: BaseListItem> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem || oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}