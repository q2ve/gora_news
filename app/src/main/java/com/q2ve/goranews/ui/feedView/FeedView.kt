/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.feedView

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.q2ve.goranews.R
import com.q2ve.goranews.databinding.ViewFeedBinding
import com.q2ve.goranews.repository.RepositoryInterface
import com.q2ve.goranews.repository.network.apiRequestsParameters.NewsCategories
import com.q2ve.goranews.ui.FeedLoadStatus


class FeedView: FeedViewInterface {
	var viewModel: FeedViewModelInterface? = null
	
	override fun getView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		category: NewsCategories?,
		repository: RepositoryInterface,
		showDummies: Int?
	): View {
		val binding = ViewFeedBinding.inflate(inflater, container, false)
		val context = inflater.context
		val viewModel: FeedViewModelInterface = FeedViewModel(category, repository)
		this.viewModel = viewModel
		
		val recycler = binding.feedRecycler
		val adapter = FeedViewAdapter({ viewModel.onArticleClicked(it, context) }, showDummies)
		recycler.adapter = adapter
		recycler.setHasFixedSize(true)
		
		//Dummy listener is necessary to restrict any interaction with recycler.
		val dummyOnTouchListener = object: RecyclerView.OnItemTouchListener {
			override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent) = true
			override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) = Unit
			override fun onRequestDisallowInterceptTouchEvent(stub: Boolean) = Unit
		}
		recycler.addOnItemTouchListener(dummyOnTouchListener)
		
		viewModel.articles?.subscribe({ articles ->
			val articlesSets = buildList<ArticleSet?> { for (i in 1..20) this.add(null) }.toMutableList()
			
			fun onBitmapsCollected() {
				val defaultAlpha = recycler.alpha
				val duration = 400L
				ViewCompat.animate(recycler)
					.alpha(0F)
					.setInterpolator(DecelerateInterpolator())
					.setDuration(duration)
					.setStartDelay(duration) //Just for shimmer demonstration ;)
					.withEndAction {
						adapter.updateData(articlesSets)
						binding.feedShimmer.hideShimmer()
						recycler.removeOnItemTouchListener(dummyOnTouchListener)
						ViewCompat.animate(recycler)
							.alpha(defaultAlpha)
							.setInterpolator(AccelerateDecelerateInterpolator())
							.setDuration(duration)
							.setStartDelay(duration)
							.start()
					}
					.start()
			}
			
			// Error may occur multiple times for a single request.
			// Even before successful response.
			// So i had to do this. Sorry.
			val usedIndexes = mutableListOf<Int>()
			articles.forEachIndexed { index, it ->
				Glide.with(context.applicationContext)
					.asBitmap()
					.load(it.urlToImage)
					.error(R.drawable.pc_broken_image_stub)
					.centerCrop()
					.fitCenter()
					.into(object: CustomTarget<Bitmap>(){
						override fun onResourceReady(
							resource: Bitmap, transition: Transition<in Bitmap>?
						) {
							articlesSets[index] = ArticleSet(it, resource)
							if (!usedIndexes.contains(index)) usedIndexes.add(index)
							if (usedIndexes.size == articles.size) onBitmapsCollected()
						}
						override fun onLoadCleared(placeholder: Drawable?) { }
						override fun onLoadFailed(errorDrawable: Drawable?) {
							if (!usedIndexes.contains(index)) {
								usedIndexes.add(index)
								articlesSets[index] = ArticleSet(it, errorDrawable?.toBitmap())
							}
							if (usedIndexes.size == articles.size) onBitmapsCollected()
						}
					})
			}
		})
		viewModel.loadNews()
		return binding.root
	}
	
	/**
	 * Returns null if viewModel or it's observable doesn't exist.
	 */
	override fun subscribeOnLoadingStatus(callback: (FeedLoadStatus) -> Unit): Unit? {
		viewModel?.loadStatus?.let {
			it.subscribe(callback)
			return Unit
		} ?: return null
	}
}