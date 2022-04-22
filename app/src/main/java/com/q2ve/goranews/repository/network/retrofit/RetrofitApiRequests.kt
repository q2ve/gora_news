/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network.retrofit

import com.q2ve.goranews.repository.database.dataclassesInterfaces.ItemArticleInterface
import com.q2ve.goranews.repository.network.retrofit.dataclasses.RetrofitItemResponseArticles
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitApiRequests {
	@GET("top-headlines?")
	fun <T: ItemArticleInterface?> getNews (
		@Header("X-Api-Key") apiKey: String,
		@Query("language") language: String,
		@Query("country") country: String,
		@Query("category") category: String,
		@Query("q") q: String,
		@Query("sortBy") sortBy: String,
		@Query("pageSize") pageSize: Int,
		@Query("page") page: Int,
	): Call<RetrofitItemResponseArticles<T>>
}