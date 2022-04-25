/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui

import com.q2ve.goranews.R
import com.q2ve.goranews.repository.network.NetworkErrorType

enum class FeedLoadStatus(private val defaultMessageResource: Int) {
	Loading(R.string.downloading_news),
	Loaded(R.string.news),
	LoadedOffline(R.string.downloaded_offline),
	Error(R.string.an_error_occurred);
	
	var errorType: NetworkErrorType? = null
	
	fun getDefaultMessage(): Int {
		return if (errorType == NetworkErrorType.NoConnection && this == LoadedOffline) {
			LoadedOffline.defaultMessageResource
		} else errorType?.getDefaultMessage() ?: this.defaultMessageResource
	}
}