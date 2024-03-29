package com.jalexy.pussygallery.mvp.view.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jalexy.pussygallery.databinding.HolderErrorBinding
import com.jalexy.pussygallery.databinding.HolderLoaderBinding

// маркер для элементов списка
interface Item {
    fun getType(): ItemType
}

enum class ItemType {
    ITEM, LOADER, ERROR
}


abstract class BaseRecyclerViewAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_TYPE = 0
        const val LOADER_TYPE = 1
        const val LOAD_ERROR_TYPE = 2
    }

    protected var items = ArrayList<Item>()

    private val loader = object : Item {
        override fun getType(): ItemType {
            return ItemType.LOADER
        }
    }

    private val error = object : Item {
        override fun getType(): ItemType {
            return ItemType.ERROR
        }
    }

    abstract fun onRetryClick()

    abstract fun onCreateItemHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun onBindItemHolder(holder: RecyclerView.ViewHolder, position: Int, viewType: Int)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.setHasFixedSize(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LOADER_TYPE -> LoaderHolder(
                HolderLoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            LOAD_ERROR_TYPE -> ErrorHolder(
                HolderErrorBinding.inflate(LayoutInflater.from(parent.context),parent, false)
            )
            else -> onCreateItemHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            LOAD_ERROR_TYPE, LOADER_TYPE -> return
            else -> onBindItemHolder(holder, position, holder.itemViewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].getType()) {
            ItemType.ITEM -> ITEM_TYPE
            ItemType.LOADER -> LOADER_TYPE
            ItemType.ERROR -> LOAD_ERROR_TYPE
        }
    }

    override fun getItemCount() = items.size

    fun addItems(newItems: ArrayList<out Item>) {
        val lastCount = items.count()

        items.addAll(newItems)
        notifyItemRangeInserted(lastCount, newItems.count())
    }

    fun setPussies(newItems: List<out Item>) {
        //todo заменить на сабмит у ListAdapter
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addItem(newItem: Item) {
        items.add(newItem)
        notifyItemInserted(items.count() - 1)
    }

    fun getItem(position: Int) = items[position]

    fun removeItem(item: Item) {
        if (items.isNotEmpty() && items.contains(item)) {
            val position = items.indexOf(item)
            items.remove(item)

            notifyItemRemoved(position)
        }
    }

    fun isEmpty() = items.isEmpty()

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    fun addLoader() {
        hasLoader().not().let {
            items.add(loader)
            notifyItemInserted(itemCount)
        }
    }

    fun removeLoader() {
        hasLoader().let {
            items.remove(loader)
            notifyItemRemoved(itemCount)
        }
    }

    fun addError() {
        hasError().not().let {
            removeLoader()
            items.add(error)
            notifyItemInserted(itemCount)
        }
    }

    fun removeError() {
        hasError().let {
            items.remove(error)
            notifyItemRemoved(itemCount)
        }
    }

    fun hasLoader() = items.contains(loader)
    fun hasError() = items.contains(error)

    // класс для вставки анимации загрузки в конец списка
    private inner class LoaderHolder(binding: HolderLoaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    // класс для показа ошибки загрузки в конце списка
    private inner class ErrorHolder(binding: HolderErrorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryBtn.setOnClickListener {
                removeError()
                onRetryClick()
            }
        }
    }
}