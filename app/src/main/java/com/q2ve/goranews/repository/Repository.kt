/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository

import com.q2ve.goranews.repository.callbackSets.ArticlesSet
import com.q2ve.goranews.repository.network.NetworkErrorType
import com.q2ve.goranews.repository.network.apiRequestsParameters.NewsCategories
import com.q2ve.goranews.repository.network.apiRequestsParameters.NewsLanguages
import com.q2ve.goranews.repository.network.apiRequestsParameters.NewsSearchScope
import com.q2ve.goranews.repository.network.apiRequestsParameters.NewsSorting

class Repository: RepositoryInterface {
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
		onSuccess: (ArticlesSet) -> Unit,
		onError: (NetworkErrorType) -> Unit,
		category: NewsCategories,
		language: NewsLanguages,
		country: String,
		query: String,
		sortOrder: NewsSorting,
		page: Int,
		vararg searchScopes: NewsSearchScope
	) {
		TODO()
	}
}