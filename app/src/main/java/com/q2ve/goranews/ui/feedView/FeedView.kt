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

class FeedView{
	var viewModel: FeedViewModelInterface? = null
	
	fun getView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		category: NewsCategories?,
		repository: RepositoryInterface
	): View {
		val binding = ViewFeedBinding.inflate(inflater, container, false)
		val context = inflater.context
		val viewModel: FeedViewModelInterface = FeedViewModel(category, repository)
		this.viewModel = viewModel
		
		val recycler = binding.feedRecycler
		val adapter = FeedViewAdapter({ viewModel.onArticleClicked(it, context) })
		recycler.adapter = adapter
		recycler.setHasFixedSize(true)
		val dummyOnTouchListener = object: RecyclerView.OnItemTouchListener {
			override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent) = true
			override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) = Unit
			override fun onRequestDisallowInterceptTouchEvent(stub: Boolean) = Unit
		}
		recycler.addOnItemTouchListener(dummyOnTouchListener)
		
		viewModel.articles?.subscribe({
			val defaultAlpha = recycler.alpha
			val duration = 400L
			
			ViewCompat.animate(recycler)
				.alpha(0F)
				.setInterpolator(DecelerateInterpolator())
				.setDuration(duration)
				.withEndAction {
					adapter.updateData(it)
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
			
		})
		
		viewModel.loadNews()
		
		return binding.root
	}
	
	/**
	 * Returns null if viewModel or it's observable doesn't exist.
	 */
	fun subscribeOnLoadingStatus(callback: (FeedLoadStatus) -> Unit, key: String): Unit? {
		viewModel?.loadStatus?.let {
			it.subscribe(callback, key)
			return Unit
		} ?: return null
	}
	
	/**
	 * Returns null if viewModel or it's observable doesn't exist.
	 */
	fun unsubscribeFromLoadingStatus(key: String): Unit? {
		viewModel?.loadStatus?.let {
			it.unsubscribe(key)
			return Unit
		} ?: return null
	}
}