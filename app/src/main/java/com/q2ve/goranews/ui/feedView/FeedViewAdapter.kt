/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.feedView

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.q2ve.goranews.R
import com.q2ve.goranews.databinding.ViewArticleItemBinding
import com.q2ve.goranews.helpers.ButtonAnimator
import com.q2ve.goranews.repository.database.realm.dataclasses.RealmItemArticle


class FeedViewAdapter(
	private val onItemClicked: ((RealmItemArticle) -> Unit)?,
	showDummyItems: Int? = 5
): RecyclerView.Adapter<FeedViewAdapter.RecyclerItemHolder> () {
	private var articles: List<RealmItemArticle> = emptyList()
	private var dummed = false
	
	init {
		if (showDummyItems != null) {
			val stub = buildList { for (i in 1..showDummyItems) { this.add(RealmItemArticle()) } }
			articles = stub
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
			val article = articles[position]
			holder.title.text = article.title
			
			if (!dummed) {
				ButtonAnimator(holder.itemView).animateWeakPressing()
				holder.itemView.setOnClickListener { onItemClicked?.invoke(article) }
				Glide.with(holder.itemView.context)
					.load(article.urlToImage)
					.transition(DrawableTransitionOptions.withCrossFade())
					.placeholder(R.drawable.pc_no_image_stub)
					.error(R.drawable.pc_broken_image_stub)
					.fallback(R.drawable.pc_broken_image_stub)
					.fitCenter()
					.centerCrop()
					.into(holder.cover)
			}
			
			//The "fit" and "centerCrop" are necessary to good performance.
//			Picasso.get()
//				.load(article.urlToImage)
//				//.networkPolicy(NetworkPolicy.OFFLINE, NetworkPolicy.NO_CACHE)
//				.fit()
//				.centerCrop()
//				.placeholder(R.drawable.pc_no_image_stub)
//				.into(holder.cover)
		}
	}
	
	override fun getItemCount(): Int = articles.size
	
	fun updateData(articles: List<RealmItemArticle>) {
		this.articles = articles
		dummed = false
		notifyDataSetChanged() //TODO("DiffUtils.")
	}
}