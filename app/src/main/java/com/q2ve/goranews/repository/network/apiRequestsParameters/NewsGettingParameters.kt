/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network.apiRequestsParameters

data class NewsGettingParameters (
	val apiKey: String,
	val category: String,
	val pageSize: Int,
	val page: Int = 1,
	val query: String = "",
	val sortOrder: String = "publishedAt",
	val language: String = "",
	val country: String = ""
)