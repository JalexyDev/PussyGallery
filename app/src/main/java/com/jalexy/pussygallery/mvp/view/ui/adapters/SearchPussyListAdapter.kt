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
import com.jalexy.pussygallery.mvp.presenter.PussySearchPresenter
import com.jalexy.pussygallery.mvp.view.PussyHolderView
import com.jalexy.pussygallery.mvp.view.ui.PussyActivity
import kotlinx.android.synthetic.main.holder_pussy.view.*

class SearchPussyListAdapter(
    private val context: Context,
    private val presenter: PussySearchPresenter
) : BaseRecyclerViewAdapter() {

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

    private inner class PussyHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), PussyHolderView {

        private lateinit var pussy: MyPussy
        private val image = itemView.pussy_image
        private val favoriteBtn = itemView.favorite_btn

        fun bind(pussyItem: MyPussy) {
            pussy = pussyItem

            GlideApp.with(context)
                .load(pussy.url)
                .placeholder(R.drawable.ic_placeholder)
                .centerCrop()
                .into(image)

            image.setOnClickListener{
                //todo переход к просмотру фотки
                context.startActivity(
                    Intent(context, PussyActivity::class.java).apply {
                        putExtra(PussyActivity.IMAGE_URL, pussy.url)
                    })
            }

            setPussyFavorite(pussy.isFavorite)

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

            pussy.isFavorite = isFavorite
            favoriteBtn.isEnabled = true
        }
    }
}