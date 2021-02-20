package com.timeweb.checkdomain.presentation.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


open class BasePagingAdapter<T : BaseListItem>(diffUtilsCallback: DiffUtil.ItemCallback<T> = BaseListItemDiffUtilsCallback()) :
    PagingDataAdapter<T, BasePagingAdapter<T>.BaseViewHolder>(diffUtilsCallback),
    OnItemSwipeListener {

    var onItemClick: ((listItem: BaseListItem) -> Unit)? = null
    var onItemDismiss: ((listItem: BaseListItem?, pos: Int) -> Unit)? = null

    override fun onItemSwipe(pos: Int) {
        onItemDismiss?.invoke(peek(pos), pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType != -1) {
            val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            BaseViewHolder(view)
        } else {
            BaseViewHolder(View(parent.context))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            holder.renderItem(repoItem)
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position)?.viewId ?: -1

    open inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view), Swipeable {

        init {
            itemView.setOnClickListener {
                val repoItem = getItem(bindingAdapterPosition)
                if (repoItem != null) {
                    onItemClick?.invoke(repoItem)
                }
            }
        }

        override fun canSwipe() = getItem(bindingAdapterPosition)?.canSwipe() ?: false

        override fun getSwipeableViews() =
            getItem(bindingAdapterPosition)?.getSwipeableViews(itemView) ?: emptyList()

        open fun renderItem(holderItem: BaseListItem) {
            holderItem.renderView(itemView, bindingAdapterPosition)
        }
    }
}
