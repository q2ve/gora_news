/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network.retrofit

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
		fun <T> buildCallback(
			onCallbackSuccess: ((Response<T>) -> Unit)?,
			onCallbackError: ((NetworkErrorType) -> Unit)?
		): Callback<T> {
			return object: Callback<T> {
				override fun onResponse(
					call: Call<T>,
					response: Response<T>
				) {
					val errorType = RetrofitErrorChecker().checkResponse(response)
					if (errorType != null) onCallbackError?.invoke(errorType)
					else onCallbackSuccess?.invoke(response)
				}
				override fun onFailure(
					call: Call<T>,
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
		fun onCallbackSuccess(response: Response<RetrofitItemResponseArticles<T>>) {
			val errorType = errorChecker.checkResponseArticles(response)
			if (errorType != null) onError?.invoke(errorType)
			else onSuccess?.invoke(response.body()?.articles)
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