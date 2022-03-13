package com.jalexy.pussygallery.mvp.view.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jalexy.pussygallery.GlideApp
import com.jalexy.pussygallery.R
import com.jalexy.pussygallery.databinding.HolderPussyBinding
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import com.jalexy.pussygallery.mvp.view.ui.PussyActivity
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class PussyRecyclerViewAdapter @Inject constructor(
    @ActivityContext private val context: Context,
) : BaseRecyclerViewAdapter() {

    var retryLoadListener: (() -> Unit)? = null
    var setFavoriteStateListener: ((pussy: MyPussy, callback: (Boolean) -> Unit) -> Unit)? = null
    var favoriteClickedListener: ((pussy: MyPussy, callback: (Boolean) -> Unit) -> Unit)? = null

    override fun onRetryClick() {
        retryLoadListener?.invoke()
    }

    override fun onCreateItemHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PussyHolder(
            HolderPussyBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        )
    }

    override fun onBindItemHolder(holder: RecyclerView.ViewHolder, position: Int, viewType: Int) {
        if (holder.itemViewType == ITEM_TYPE) {
            (holder as PussyHolder).bind(items[position] as MyPussy)
        }
    }

    fun updateItem(item: Item) {
        val position = items.indexOf(item)
        if (position == -1) return

        if (item is MyPussy) {
            //todo заменить это дерьмо на нормальное обновление через состояние итема
//            holders[item.pussyId]?.setPussyFavorite(item.isInFavorite())
        }
    }

    private inner class PussyHolder(val binding: HolderPussyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pussyItem: MyPussy) {
            GlideApp.with(context)
                .load(pussyItem.url)
                .placeholder(R.drawable.ic_placeholder)
                .centerCrop()
                .into(binding.pussyImage)

            binding.pussyImage.setOnClickListener {
                context.startActivity(
                    Intent(context, PussyActivity::class.java).apply {
                        putExtra(PussyActivity.IMAGE_URL, pussyItem.url)
                    })
            }

            setFavoriteStateListener?.invoke(pussyItem) { isFavorite -> setPussyFavorite(isFavorite) }

            binding.favoriteBtn.setOnClickListener {
                it.isEnabled = false
                favoriteClickedListener?.invoke(pussyItem) { isFavorite ->
                    setPussyFavorite(
                        isFavorite
                    )
                }
            }
        }

        private fun setPussyFavorite(isFavorite: Boolean) {
            binding.favoriteBtn.setImageResource(
                if (isFavorite)
                    R.drawable.selector_favorite_selected
                else
                    R.drawable.selector_favorite_enable
            )

            binding.favoriteBtn.isEnabled = true
        }
    }
}