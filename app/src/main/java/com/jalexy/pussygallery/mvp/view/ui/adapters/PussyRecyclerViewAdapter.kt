package com.jalexy.pussygallery.mvp.view.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jalexy.pussygallery.GlideApp
import com.jalexy.pussygallery.R
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import com.jalexy.pussygallery.mvp.presenter.BasePresenter
import com.jalexy.pussygallery.mvp.view.PussyHolderView
import com.jalexy.pussygallery.mvp.view.ui.PussyActivity
import kotlinx.android.synthetic.main.holder_pussy.view.*

class PussyRecyclerViewAdapter(
    private val context: Context,
    private val presenter: BasePresenter
) : BaseRecyclerViewAdapter() {

    private val holders: ArrayList<PussyHolderView> by lazy {
        ArrayList<PussyHolderView>()
    }

    override fun onRetryClick() {
        presenter.retryLoad()
    }

    override fun onCreateItemHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PussyHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.holder_pussy, viewGroup, false)
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

        holders[position].setPussyFavorite((item as MyPussy).isInFavorite())
    }

    private inner class PussyHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), PussyHolderView {

        private lateinit var pussy: MyPussy
        private val image = itemView.pussy_image
        private val favoriteBtn = itemView.favorite_btn

        fun bind(pussyItem: MyPussy) {

            holders.add(this)

            pussy = pussyItem

            GlideApp.with(context)
                .load(pussy.url)
                .placeholder(R.drawable.ic_placeholder)
                .centerCrop()
                .into(image)

            image.setOnClickListener{
                context.startActivity(
                    Intent(context, PussyActivity::class.java).apply {
                        putExtra(PussyActivity.IMAGE_URL, pussy.url)
                    })
            }

            presenter.setFavoriteState(this, pussy)

            favoriteBtn.setOnClickListener {
                it.isEnabled = false
                presenter.favoriteClicked(this, pussy)
            }
        }

        override fun setPussyFavorite(isFavorite: Boolean) {
            favoriteBtn.setImageResource(
                if (isFavorite)
                    R.drawable.selector_favorite_selected
                else
                    R.drawable.selector_favorite_enable
            )

            favoriteBtn.isEnabled = true
        }
    }
}