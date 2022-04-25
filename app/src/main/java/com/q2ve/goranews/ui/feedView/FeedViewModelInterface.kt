/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.feedView

import android.content.Context
import com.q2ve.goranews.helpers.Observable
import com.q2ve.goranews.repository.database.realm.dataclasses.RealmItemArticle
import com.q2ve.goranews.ui.FeedLoadStatus

interface FeedViewModelInterface {
	var articles: Observable<List<RealmItemArticle>>?
	var loadStatus: Observable<FeedLoadStatus>?
	
	fun setQuery(query: String)
	
	fun loadNews()
	fun onArticleClicked(article: RealmItemArticle, context: Context)
	fun emptyArticlesList()
}