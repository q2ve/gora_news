/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.q2ve.goranews.databinding.FragmentSearchBinding
import com.q2ve.goranews.helpers.ButtonAnimator
import com.q2ve.goranews.helpers.hideKeyboard
import com.q2ve.goranews.repository.Repository
import com.q2ve.goranews.repository.RepositoryInterface
import com.q2ve.goranews.ui.feedView.FeedView
import java.util.*

class SearchFragment: Fragment() {
	private val repository: RepositoryInterface = Repository()
	private lateinit var binding: FragmentSearchBinding
	private var feed: FeedView? = null
	private val subscribeKey = UUID.randomUUID().toString()
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentSearchBinding.inflate(inflater, container, false)
		
		val categoryFeed = FeedView()
		this.feed = categoryFeed
		val feedView = categoryFeed.getView(inflater, container, null, repository, null, 100)
		binding.fragmentSearchFeedFrame.addView(feedView)
		
		val title = binding.fragmentSearchTitle
		categoryFeed.viewModel?.loadStatus?.subscribe({
			val resource = it.getDefaultMessage()
			title.text = resources.getString(resource)
		}, subscribeKey)
		
		val searchBox = binding.fragmentSearchSearchFrame
		SearchFieldPresenter().placeSearchField(
			searchBox,
			inflater,
			{
				if (it != "") {
					categoryFeed.viewModel?.setQuery(it)
					categoryFeed.viewModel?.loadNews()
				}
			},
			{ hideKeyboard() },
			800
		)
		
		val closeButton = binding.fragmentSearchBackButton
		ButtonAnimator(closeButton).animateStrongPressingWithFading()
		closeButton.setOnClickListener { activity?.onBackPressed() }
		
		return binding.root
	}
	
	override fun onDestroyView() {
		feed?.viewModel?.loadStatus?.unsubscribe(subscribeKey)
		super.onDestroyView()
	}
}