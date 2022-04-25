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

interface RepositoryInterface {
	/**
	 * Returns downloaded articles.
	 * If download is successful - will call "onSuccess"
	 * with null error type.
	 * Without connection to the server will try
	 * to get requested articles from the database.
	 * If it finds them in database - will call "onSuccess"
	 * with type of the network error.
	 * If it doesn't find them there - will call "onError".
	 */
	fun getNews(
		category: NewsCategories?,
		onSuccess: (ArticlesSet, NetworkErrorType?) -> Unit,
		onError: (NetworkErrorType) -> Unit,
		language: NewsLanguages = NewsLanguages.All,
		country: String = "", //It's too boring to create enum for these 54 countries. Especially when we don't use them
		query: String = "",
		sortOrder: NewsSorting = NewsSorting.PublishedAt,
		page: Int = 1, //Yes, from "1"
		vararg searchScopes: NewsSearchScope = arrayOf(NewsSearchScope.Title, NewsSearchScope.Description)
	)
}