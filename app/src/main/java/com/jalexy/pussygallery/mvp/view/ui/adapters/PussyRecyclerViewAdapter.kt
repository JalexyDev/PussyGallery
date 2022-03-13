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
import com.jalexy.pussygallery.mvp.presenter.BasePresenter
import com.jalexy.pussygallery.mvp.view.PussyHolderView
import com.jalexy.pussygallery.mvp.view.PussyListFragmentView
import com.jalexy.pussygallery.mvp.view.ui.PussyActivity

class PussyRecyclerViewAdapter(
    private val context: Context,
    private val presenter: BasePresenter<out PussyListFragmentView>
) : BaseRecyclerViewAdapter() {

    private val holders: HashMap<String, PussyHolderView> by lazy {
        HashMap<String, PussyHolderView>()
    }

    override fun onRetryClick() {
        presenter.retryLoad()
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
            holders[item.pussyId]?.setPussyFavorite(item.isInFavorite())
        }
    }

    private inner class PussyHolder(val binding: HolderPussyBinding) :
        RecyclerView.ViewHolder(binding.root), PussyHolderView {

        fun bind(pussyItem: MyPussy) {

            holders[pussyItem.pussyId] = this

            GlideApp.with(context)
                .load(pussyItem.url)
                .placeholder(R.drawable.ic_placeholder)
                .centerCrop()
                .into(binding.pussyImage)

            binding.pussyImage.setOnClickListener{
                context.startActivity(
                    Intent(context, PussyActivity::class.java).apply {
                        putExtra(PussyActivity.IMAGE_URL, pussyItem.url)
                    })
            }

            presenter.setFavoriteState(this, pussyItem)

            binding.favoriteBtn.setOnClickListener {
                it.isEnabled = false
                presenter.favoriteClicked(this, pussyItem)
            }
        }

        override fun setPussyFavorite(isFavorite: Boolean) {
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