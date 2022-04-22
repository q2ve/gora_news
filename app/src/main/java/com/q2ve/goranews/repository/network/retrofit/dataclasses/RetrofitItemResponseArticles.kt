/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network.retrofit.dataclasses

import com.q2ve.goranews.repository.database.realm.dataclasses.RealmItemArticle

data class RetrofitItemResponseArticles (
	val status: String?,
	val totalResults: Int?,
	val articles: List<RealmItemArticle>?,
	val code: String?,
	val message: String?
)
