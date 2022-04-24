/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.mainFeed

import com.q2ve.goranews.helpers.Observable
import com.q2ve.goranews.ui.FeedLoadStatus

interface MainFeedViewModelInterface {
	var loadStatus: Observable<FeedLoadStatus>?
	
	fun onCreate()
	fun onCreateView()
	fun onViewCreated()
	fun onDestroyView()
}