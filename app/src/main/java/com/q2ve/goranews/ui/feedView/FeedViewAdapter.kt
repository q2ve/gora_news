/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.feedView

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.q2ve.goranews.R
import com.q2ve.goranews.databinding.ViewArticleItemBinding
import com.q2ve.goranews.helpers.ButtonAnimator
import com.q2ve.goranews.repository.database.realm.dataclasses.RealmItemArticle
import java.util.*


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
				
				val context = holder.itemView.context
				Glide.with(context)
					.load(article.urlToImage)
					.transition(DrawableTransitionOptions.withCrossFade(150))
					.apply(buildGlideConfig(context))
					.into(holder.cover)
			}
		}
	}
	
	override fun getItemCount(): Int = articles.size
	
	fun updateData(articles: List<RealmItemArticle>) {
		this.articles = articles
		dummed = false
		notifyDataSetChanged() //TODO("DiffUtils.")
	}
	
	/**
	 * Returns preloader which can *wow* preload images before recycler will displays them.
	 */
	fun buildPreloader(
		context: Context, maxPreload: Int
	): RecyclerViewPreloader<String> {
		val modelProvider = object: ListPreloader.PreloadModelProvider<String> {
			override fun getPreloadItems(position: Int): MutableList<String> {
				Log.e("getPreloadItems", articles[position].urlToImage.toString())
				val url = articles[position].urlToImage
				return if (TextUtils.isEmpty(url)) Collections.emptyList()
				else Collections.singletonList(url)
			}
			override fun getPreloadRequestBuilder(item: String): RequestBuilder<*> {
				Log.e("getPreloadRequestBuilder", item)
				return Glide.with(context)
					.load(item)
					.transition(DrawableTransitionOptions.withCrossFade(150))
					.apply(buildGlideConfig(context))
			}
		}
		val dimens = getViewDimensions(context)
		val sizeProvider = FixedPreloadSizeProvider<String>(dimens.first, dimens.second)
		return RecyclerViewPreloader(Glide.with(context), modelProvider, sizeProvider, maxPreload)
	}
	
	private fun buildGlideConfig(context: Context): RequestOptions {
		val dimens = getViewDimensions(context)
		return RequestOptions()
			.placeholder(R.drawable.pc_no_image_stub)
			.error(R.drawable.pc_broken_image_stub)
			.override(dimens.first, dimens.second)
	}
	
	private fun getViewDimensions(context: Context): Pair<Int, Int> {
		val layout = context.resources.getLayout(R.layout.view_article_item)
		val xmlns = "http://schemas.android.com/apk/res/android"
		val layoutWidth = layout.getAttributeIntValue(xmlns, "layout_width", 180)
		val layoutHeight = layout.getAttributeIntValue(xmlns, "layout_height", 280)
		val dpCoefficient = context.resources.displayMetrics.density + 0.5F
		val width = (layoutWidth * dpCoefficient).toInt()
		val height = (layoutHeight * dpCoefficient).toInt()
		return Pair(width, height)
	}
}