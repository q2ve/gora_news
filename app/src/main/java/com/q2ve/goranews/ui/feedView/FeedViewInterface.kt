/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.feedView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.q2ve.goranews.repository.RepositoryInterface
import com.q2ve.goranews.repository.network.apiRequestsParameters.NewsCategories
import com.q2ve.goranews.ui.FeedLoadStatus

interface FeedViewInterface {
	fun getView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		category: NewsCategories?,
		repository: RepositoryInterface,
		showDummies: Int? = 5
	): View
	
	/**
	 * Returns null if viewModel or it's observable doesn't exist.
	 */
	fun subscribeOnLoadingStatus(callback: (FeedLoadStatus) -> Unit): Unit?
}