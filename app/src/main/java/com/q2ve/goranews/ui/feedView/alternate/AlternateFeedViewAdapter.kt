/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.feedView.alternate

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.q2ve.goranews.databinding.ViewArticleItemBinding
import com.q2ve.goranews.helpers.ButtonAnimator
import com.q2ve.goranews.repository.database.realm.dataclasses.RealmItemArticle
import com.q2ve.goranews.ui.feedView.ArticleSet


class AlternateFeedViewAdapter(
	private val onItemClicked: ((RealmItemArticle) -> Unit)?,
	showDummyItems: Int? = 5
): RecyclerView.Adapter<AlternateFeedViewAdapter.RecyclerItemHolder> () {
	private var articlesSets: List<ArticleSet?> = emptyList()
	private var dummed = false
	
	init {
		if (showDummyItems != null) {
			val stub = buildList { for (i in 1..showDummyItems) {
				this.add(ArticleSet(RealmItemArticle(), null)) }
			}
			articlesSets = stub
			dummed = true
		}
	}
	
	class RecyclerItemHolder(
		viewBinding: ViewArticleItemBinding
	): RecyclerView.ViewHolder(viewBinding.root) {
		var cover: ImageView = viewBinding.articleItemImageView
		var title: TextView = viewBinding.articleItemTextView
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemHolder {
		val binding = ViewArticleItemBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)
		return RecyclerItemHolder(binding)
	}
	
	override fun onBindViewHolder(holder: RecyclerItemHolder, position: Int) {
		if (position < itemCount) {
			val articleSet = articlesSets[position]
			holder.title.text = articleSet?.article?.title
			
			if (!dummed && articleSet != null) {
				ButtonAnimator(holder.itemView).animateWeakPressing()
				holder.itemView.setOnClickListener { onItemClicked?.invoke(articleSet.article) }
				holder.cover.setImageBitmap(articleSet.image)
			}
		}
	}
	
	override fun getItemCount(): Int = articlesSets.size
	
	fun updateData(articlesSets: List<ArticleSet?>) {
		this.articlesSets = articlesSets
		dummed = false
		notifyDataSetChanged() //TODO("DiffUtils.")
	}
}