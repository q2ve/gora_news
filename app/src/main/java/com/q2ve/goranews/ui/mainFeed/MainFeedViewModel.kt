/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.mainFeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.q2ve.goranews.R
import com.q2ve.goranews.helpers.Constants
import com.q2ve.goranews.helpers.Observable
import com.q2ve.goranews.helpers.navigator.Navigator
import com.q2ve.goranews.helpers.navigator.NavigatorAnimation
import com.q2ve.goranews.repository.RepositoryInterface
import com.q2ve.goranews.repository.network.apiRequestsParameters.NewsCategories
import com.q2ve.goranews.ui.FeedLoadStatus
import com.q2ve.goranews.ui.feedView.FeedView
import com.q2ve.goranews.ui.feedView.alternate.AlternateFeedView
import com.q2ve.goranews.ui.search.SearchFragment

class MainFeedViewModel: ViewModel(), MainFeedViewModelInterface {
	override var loadStatus: Observable<FeedLoadStatus>? = null
	override var feeds: Observable<List<CategorySet>>? = null
	
	override fun onCreate() {
		loadStatus = Observable(FeedLoadStatus.Loading)
		feeds = Observable(emptyList())
	}
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		repository: RepositoryInterface
	) {
		val resources = inflater.context.resources
		val categoriesFeeds = mutableListOf<CategorySet>()
		fun getCategoryName(it: NewsCategories) = resources.getString(it.getNameResource())
		
		NewsCategories.values().sortedBy{ getCategoryName(it) }.forEachIndexed { index, it ->
			val categoryFeed = if (Constants.useAlternativeFeed) AlternateFeedView() else FeedView()
			val view = categoryFeed.getView(inflater, container, it, repository)
			categoryFeed.subscribeOnLoadingStatus { updateStatusList(it, index) }
			val categorySet = CategorySet(getCategoryName(it),view)
			categoriesFeeds.add(categorySet)
		}
		loadStatus?.value = FeedLoadStatus.Loading
		feeds?.value = categoriesFeeds
	}
	
	override fun onSearchClicked() {
		val frame = R.id.activity_main_frame
		val animation = NavigatorAnimation.SlideDownBounce
		Navigator.addFragment(SearchFragment(), frame, animation, true)
	}
	
	override fun onDestroy() {
		loadStatus = null
		feeds = null
	}
	
	// Needs to collect load statuses of each category and set general status in observable.
	private val statusList = mutableMapOf<Int, FeedLoadStatus>()
	private fun updateStatusList(status: FeedLoadStatus, index: Int) {
		statusList[index] = status
		val tempStatus = when {
			(statusList.values.contains(FeedLoadStatus.Loading)) -> FeedLoadStatus.Loading
			(statusList.values.contains(FeedLoadStatus.Error)) -> FeedLoadStatus.Error
			(statusList.values.contains(FeedLoadStatus.LoadedOffline)) -> FeedLoadStatus.LoadedOffline
			else -> FeedLoadStatus.Loaded
		}
		if (tempStatus != loadStatus?.value) loadStatus?.value = tempStatus
	}
}