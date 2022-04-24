/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui

import com.q2ve.goranews.helpers.Observable

interface FeedViewModelInterface {
	var loadStatus: Observable<FeedLoadStatus>?
	
	fun onCreateView()
	fun onViewCreated()
	fun onDestroyView()
}