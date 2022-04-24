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
import com.q2ve.goranews.repository.Repository
import com.q2ve.goranews.repository.RepositoryInterface
import com.q2ve.goranews.repository.network.apiRequestsParameters.NewsCategories
import com.q2ve.goranews.ui.feedView.FeedView

class MainFeedFragment: Fragment() {
	private val repository: RepositoryInterface = Repository()
	private lateinit var viewModel: MainFeedViewModelInterface
	private lateinit var binding: FragmentMainFeedBinding
	
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
		viewModel.onCreateView()
		binding = FragmentMainFeedBinding.inflate(inflater, container, false)
		
		val resources = inflater.context.resources
		fun getCategoryName(it: NewsCategories) = resources.getString(it.getNameResource())
		val categoriesFeeds = mutableListOf<CategorySet>()
		
		NewsCategories.values().sortedBy { getCategoryName(it) }.forEach {
			val categoryFeed = FeedView()
			//categoryFeed.subscribeOnLoadingStatus()
			val view = categoryFeed.getView(inflater, container, it, repository)
			categoriesFeeds.add(CategorySet(getCategoryName(it),view))
		}
		
		val recycler = binding.fragmentMainFeedRecycler
		recycler.adapter = MainFeedAdapter(categoriesFeeds)
		recycler.setHasFixedSize(true)
		
		return binding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.onViewCreated()
	}
}