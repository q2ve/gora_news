/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network.retrofit

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.q2ve.goranews.repository.NetworkInterface
import com.q2ve.goranews.repository.database.dataclassesInterfaces.ItemArticle
import com.q2ve.goranews.repository.network.NetworkErrorType
import com.q2ve.goranews.repository.network.apiRequestsParameters.NewsGettingParameters
import com.q2ve.goranews.repository.network.retrofit.dataclasses.RetrofitItemResponseArticles
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitCalls: NetworkInterface {
	private val caller = RetrofitCaller()
	private val errorChecker = RetrofitErrorChecker()
	
	private class CallbackBuilder {
		/**
		 * "R" is a specific class that represents structure of the response body.
		 * We can't pass specific class directly as the result of an ApiRequest function
		 * because it may contains nested generic itself, and retrofit can't handle it.
		 * Developers marked corresponding issue as "feature".
		 */
		inline fun <reified R> buildCallback(
			noinline onCallbackSuccess: ((R) -> Unit)?,
			noinline onCallbackError: ((NetworkErrorType) -> Unit)?
		): Callback<JsonElement> {
			return object: Callback<JsonElement> {
				override fun onResponse(
					call: Call<JsonElement>,
					response: Response<JsonElement>
				) {
					val errorType = RetrofitErrorChecker().checkResponse(response)
					if (errorType != null) onCallbackError?.invoke(errorType)
					else {
						val body = response.body()
						val serializedBody = Gson().fromJson(body?.asJsonObject, R::class.java)
						onCallbackSuccess?.invoke(serializedBody)
					}
				}
				override fun onFailure(
					call: Call<JsonElement>,
					t: Throwable
				) {
					val errorType = RetrofitErrorChecker().checkOnFailure(t)
					onCallbackError?.invoke(errorType)
				}
			}
		}
	}
	
	override fun <T: ItemArticle?> getNews(
		parameters: NewsGettingParameters,
		onSuccess: ((List<T>?) -> Unit)?,
		onError: ((NetworkErrorType) -> Unit)?
	) {
		fun onCallbackSuccess(body: RetrofitItemResponseArticles<T>) {
			val errorType = errorChecker.checkResponseArticles(body)
			if (errorType != null) onError?.invoke(errorType)
			else onSuccess?.invoke(body.articles)
		}
		
		val callback = CallbackBuilder().buildCallback(::onCallbackSuccess, onError)
		val call = caller.getRequest().getNews<T>(
			apiKey = parameters.apiKey,
			language = parameters.language,
			country = parameters.country,
			category = parameters.category,
			q = parameters.query,
			sortBy = parameters.sortOrder,
			pageSize = parameters.pageSize,
			page = parameters.page
		)
		caller.enqueueCall(call, callback)
	}
}