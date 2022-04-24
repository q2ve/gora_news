/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.q2ve.goranews.databinding.FragmentFeedBinding
import com.q2ve.goranews.repository.Repository
import com.q2ve.goranews.repository.network.apiRequestsParameters.NewsCategories
import com.q2ve.goranews.ui.feedView.FeedView

class FeedFragment: Fragment() {
	lateinit var viewModel: FeedViewModelInterface
	lateinit var binding: FragmentFeedBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
	}
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		viewModel.onCreateView()
		binding = FragmentFeedBinding.inflate(inflater, container, false)
		
		val test = FeedView().getView(
			inflater, binding.test, NewsCategories.Technology, Repository()
		)
		binding.test.addView(test)
		
		return binding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.onViewCreated()
	}
}