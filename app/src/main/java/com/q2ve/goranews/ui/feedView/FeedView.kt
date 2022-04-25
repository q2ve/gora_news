/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.feedView

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
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
		showDummies: Int?,
		startDelay: Long
	): View {
		val binding = ViewFeedBinding.inflate(inflater, container, false)
		val context = inflater.context
		val viewModel: FeedViewModelInterface = FeedViewModel(category, repository)
		this.viewModel = viewModel
		
		val recycler = binding.feedRecycler
		val adapter = FeedViewAdapter({ viewModel.onArticleClicked(it, context) }, showDummies)
		recycler.adapter = adapter
		recycler.setHasFixedSize(true)
		//recycler.setItemViewCacheSize(4)
		
		val preloader = adapter.buildPreloader(context, 8)
		recycler.addOnScrollListener(preloader)
		
		//Dummy listener is necessary to restrict any interaction with recycler.
		val dummyOnTouchListener = object: RecyclerView.OnItemTouchListener {
			override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent) = true
			override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) = Unit
			override fun onRequestDisallowInterceptTouchEvent(stub: Boolean) = Unit
		}
		recycler.addOnItemTouchListener(dummyOnTouchListener)
		
		viewModel.articles?.subscribe({ articles ->
			//articles.forEach { Glide.with(context).load(it.urlToImage).preload() } //Async preloading. Nor working.
			val defaultAlpha = recycler.alpha
			val duration = 400L
			ViewCompat.animate(recycler)
				.alpha(0F)
				.setInterpolator(DecelerateInterpolator())
				.setDuration(duration)
				.setStartDelay(startDelay) //Just for shimmer demonstration ;)
				.withEndAction {
					adapter.updateData(articles)
					binding.feedShimmer.hideShimmer()
					recycler.removeOnItemTouchListener(dummyOnTouchListener)
					ViewCompat.animate(recycler)
						.alpha(defaultAlpha)
						.setInterpolator(AccelerateDecelerateInterpolator())
						.setDuration(duration)
						.setStartDelay(startDelay)
						.start()
				}
				.start()
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