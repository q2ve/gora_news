/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.mainFeed

import android.view.LayoutInflater
import android.view.ViewGroup
import com.q2ve.goranews.helpers.Observable
import com.q2ve.goranews.repository.RepositoryInterface
import com.q2ve.goranews.ui.FeedLoadStatus

interface MainFeedViewModelInterface {
	var loadStatus: Observable<FeedLoadStatus>?
	var feeds: Observable<List<CategorySet>>?
	
	fun onCreate()
	fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		repository: RepositoryInterface
	)
	fun onDestroy()
	
	fun onSearchClicked()
}