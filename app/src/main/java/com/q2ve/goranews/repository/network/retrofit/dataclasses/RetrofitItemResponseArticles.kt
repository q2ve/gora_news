/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network.retrofit.dataclasses

import com.q2ve.goranews.repository.database.dataclassesInterfaces.ItemArticleInterface

data class RetrofitItemResponseArticles <T: ItemArticleInterface?>(
	val status: String?,
	val totalResults: Int?,
	val articles: List<T>?,
	val code: String?,
	val message: String?
)