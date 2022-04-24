/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui

import androidx.lifecycle.ViewModel
import com.q2ve.goranews.helpers.Observable

class FeedViewModel: ViewModel(), FeedViewModelInterface {
	override var loadStatus: Observable<FeedLoadStatus>? = null
	
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