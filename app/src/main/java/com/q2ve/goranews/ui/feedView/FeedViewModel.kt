/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.feedView

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import com.q2ve.goranews.helpers.Observable
import com.q2ve.goranews.repository.RepositoryInterface
import com.q2ve.goranews.repository.callbackSets.ArticlesSet
import com.q2ve.goranews.repository.database.realm.dataclasses.RealmItemArticle
import com.q2ve.goranews.repository.network.NetworkErrorType
import com.q2ve.goranews.repository.network.apiRequestsParameters.NewsCategories
import com.q2ve.goranews.ui.FeedLoadStatus

class FeedViewModel(
	private val category: NewsCategories?,
	private val repository: RepositoryInterface
): FeedViewModelInterface {
	private var query: String = ""
	
	override var articles: Observable<List<RealmItemArticle>>? = Observable(emptyList())
	override var loadStatus: Observable<FeedLoadStatus>? = Observable(FeedLoadStatus.Loading)
	
	override fun setQuery(query: String) { this.query = query }
	
	override fun loadNews() {
		if (!(category == null && query == "")) {
			loadStatus?.value = FeedLoadStatus.Loading
			repository.getNews(category, ::onNewsLoaded, ::onNewsLoadingFailed, query = query)
		}
	}
	
	private fun onNewsLoaded(articlesSet: ArticlesSet, errorType: NetworkErrorType?) {
		val status = if (articlesSet.networkErrorType == null) FeedLoadStatus.Loaded
					else FeedLoadStatus.LoadedOffline
		status.errorType = errorType
		loadStatus?.value = status
		articles?.value = articlesSet.articles
	}
	
	private fun onNewsLoadingFailed(errorType: NetworkErrorType) {
		val status = FeedLoadStatus.Error
		status.errorType = errorType
		loadStatus?.value = status
	}
	
	override fun onArticleClicked(article: RealmItemArticle, context: Context) {
		val intent = Intent(Intent.ACTION_VIEW)
		intent.data = Uri.parse(article.url)
		ContextCompat.startActivity(context, intent, null)
	}
	
	override fun emptyArticlesList() {
		articles?.value = emptyList()
	}
}