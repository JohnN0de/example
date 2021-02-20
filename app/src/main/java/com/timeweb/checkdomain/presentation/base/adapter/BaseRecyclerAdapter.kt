package com.timeweb.checkdomain.presentation.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


open class BaseRecyclerAdapter(data: List<BaseListItem> = emptyList()) :
    RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder>(), OnItemSwipeListener {

    private var dataset = mutableListOf<BaseListItem>()

    var onItemClick: ((listItem: BaseListItem, pos: Int) -> Unit)? = null
    var onItemDismiss: ((listItem: BaseListItem?, pos: Int) -> Unit)? = null

    init {
        dataset = data.toMutableList()
    }

    fun getDataSet() = dataset.toList()

    override fun onItemSwipe(pos: Int) {
        onItemDismiss?.invoke(getItem(pos), pos)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int
    ) {
        holder.renderItem(dataset[position])
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return BaseViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size

    override fun getItemViewType(position: Int): Int = dataset[position].viewId

    fun getItem(position: Int) = dataset[position]

    fun getItemPosition(item: BaseListItem): Int = dataset.indexOf(item)

    fun replaceElements(newElements: List<BaseListItem>) {
        dataset.clear()
        dataset.addAll(newElements)
    }

    fun clearElements() {
        dataset.clear()
    }

    open inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view), Swipeable {

        init {
            itemView.setOnClickListener {
                val repoItem = getItem(bindingAdapterPosition)
                onItemClick?.invoke(repoItem, bindingAdapterPosition)
            }
        }

        override fun canSwipe() = getItem(bindingAdapterPosition).canSwipe()

        override fun getSwipeableViews() =
            getItem(bindingAdapterPosition).getSwipeableViews(itemView)

        open fun renderItem(holderItem: BaseListItem) {
            holderItem.renderView(itemView, bindingAdapterPosition)
        }
    }


}