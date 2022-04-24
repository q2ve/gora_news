/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.mainFeed

import androidx.lifecycle.ViewModel
import com.q2ve.goranews.helpers.Observable
import com.q2ve.goranews.ui.FeedLoadStatus

class MainFeedViewModel: ViewModel(), MainFeedViewModelInterface {
	override var loadStatus: Observable<FeedLoadStatus>? = null
	
	override fun onCreate() {
		//TODO("Not yet implemented")
	}
	
	override fun onCreateView() {
		//TODO("Not yet implemented")
	}
	
	override fun onViewCreated() {
		//TODO("Not yet implemented")
	}
	
	override fun onDestroyView() {
		//TODO("Not yet implemented")
	}
}