/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository

import com.q2ve.goranews.helpers.Constants
import com.q2ve.goranews.repository.callbackSets.ArticlesSet
import com.q2ve.goranews.repository.database.realm.RealmIO
import com.q2ve.goranews.repository.database.realm.dataclasses.RealmItemArticle
import com.q2ve.goranews.repository.network.ApiKeyProvider
import com.q2ve.goranews.repository.network.ApiKeyProviderInterface
import com.q2ve.goranews.repository.network.NetworkErrorType
import com.q2ve.goranews.repository.network.apiRequestsParameters.*
import com.q2ve.goranews.repository.network.retrofit.RetrofitCalls

class Repository: RepositoryInterface {
	private val networkClient: NetworkInterface = RetrofitCalls()
	private val apiKeyProvider: ApiKeyProviderInterface = ApiKeyProvider()
	private val objectFilter: ObjectFilterInterface = ObjectFilter()
	private val realm = RealmIO()
	
	/**
	 * Returns downloaded articles.
	 * If download is successful - will call "onSuccess"
	 * with null error type.
	 * Without connection to the server will try
	 * to get requested articles from the database.
	 * If it finds them there - will call "onSuccess"
	 * with type of the network error.
	 * If it doesn't find them in database - will call "onError".
	 */
	override fun getNews(
		category: NewsCategories,
		onSuccess: (ArticlesSet) -> Unit,
		onError: (NetworkErrorType) -> Unit,
		language: NewsLanguages,
		country: String,
		query: String,
		sortOrder: NewsSorting,
		page: Int,
		vararg searchScopes: NewsSearchScope
	) {
		val parameters = NewsGettingParameters(
			apiKey = apiKeyProvider.getApiKey(),
			category = category.getCategoryKey(),
			pageSize = Constants.paginationStep,
			page = page,
			query = query,
			searchScope = NewsSearchScope.getCompositeKey(*searchScopes),
			sortOrder = sortOrder.getKey(),
			language = language.getKey(),
			country = country
		)
		fun onDownloadSuccess(articles: List<RealmItemArticle>) {
			val checkedArticles = objectFilter.checkList(articles)
			realm.insertOrUpdateWithIndexing<RealmItemArticle>(
				category.getCategoryKey(), checkedArticles, true
			)
			onSuccess(ArticlesSet(articles, null))
		}
		fun onDownloadFailed(errorType: NetworkErrorType) {
			realm.copyIndexedFromRealm<RealmItemArticle>(category.getCategoryKey(),
				{ offlineArticles ->
					if (offlineArticles.isEmpty()) onError(errorType) else {
						onSuccess(ArticlesSet(offlineArticles, errorType))
					}
				},
				{ onError(errorType) }
			)
		}
		networkClient.getNews(parameters, ::onDownloadSuccess, ::onDownloadFailed)
	}
}