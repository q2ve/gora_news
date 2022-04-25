/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.mainFeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.q2ve.goranews.databinding.FragmentMainFeedBinding
import com.q2ve.goranews.helpers.ButtonAnimator
import com.q2ve.goranews.repository.Repository
import com.q2ve.goranews.repository.RepositoryInterface
import java.util.*

class MainFeedFragment: Fragment() {
	private val repository: RepositoryInterface = Repository()
	private lateinit var viewModel: MainFeedViewModelInterface
	private lateinit var binding: FragmentMainFeedBinding
	private val subscribeKey = UUID.randomUUID().toString()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val viewModel = ViewModelProvider(this).get(MainFeedViewModel::class.java)
		this.viewModel = viewModel
		viewModel.onCreate()
	}
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentMainFeedBinding.inflate(inflater, container, false)
		
		val title = binding.fragmentMainFeedTitle
		viewModel.loadStatus?.subscribe({
			var resource = it.getDefaultMessage()
			title.text = resources.getString(resource)
		}, subscribeKey)
		
		val searchButton = binding.fragmentMainFeedSearchButton
		ButtonAnimator(searchButton).animateStrongPressingWithFading()
		searchButton.setOnClickListener { viewModel.onSearchClicked() }
		
		val recycler = binding.fragmentMainFeedRecycler
		recycler.setHasFixedSize(true)
		viewModel.feeds?.subscribe({ recycler.adapter = MainFeedAdapter(it) }, subscribeKey)
		
		viewModel.onCreateView(inflater, container, repository)
		return binding.root
	}
	
	override fun onDestroyView() {
		viewModel.feeds?.unsubscribe(subscribeKey)
		viewModel.loadStatus?.unsubscribe(subscribeKey)
		super.onDestroyView()
	}
	
	override fun onDestroy() {
		viewModel.onDestroy()
		super.onDestroy()
	}
}