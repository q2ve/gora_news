/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network.retrofit

import com.q2ve.goranews.repository.network.NetworkErrorType
import com.q2ve.goranews.repository.network.retrofit.dataclasses.RetrofitItemResponseArticles
import retrofit2.Response

class RetrofitErrorChecker {
	/**
	 * Checks http codes.
	 * Returns NetworkErrorType or null if there are no errors.
	 */
	fun checkResponse(response: Response<*>): NetworkErrorType? {
		return when {
			(response.code() == 500) -> NetworkErrorType.UnknownServerError
			(response.code() == 400) -> NetworkErrorType.BadRequest
			(response.code() == 401) -> NetworkErrorType.InvalidApiKey
			(response.code() == 429) -> NetworkErrorType.Throttling
			else -> null
		}
	}
	
	/**
	 * Use if you want to know the reason of a connection failure.
	 * Returns NetworkErrorType or null if there are no errors.
	 */
	fun checkOnFailure(throwable: Throwable): NetworkErrorType {
		return when (throwable.javaClass.name.toString()) {
			//Unnecessary for now, but will become useful if extending the project.
			"java.net.SocketTimeoutException" -> NetworkErrorType.NoConnection
			"java.net.UnknownHostException" -> NetworkErrorType.NoConnection
			else -> NetworkErrorType.NoConnection
		}
	}
	
	/**
	 * Checks body's and articles' presence.
	 * Returns NetworkErrorType or null if there are no errors.
	 */
	fun checkResponseArticles(
		response: RetrofitItemResponseArticles?
	): NetworkErrorType? {
		return when {
			(response?.articles == null) -> NetworkErrorType.UnknownServerError
			//This parameters are not used in the app for now.
			//(response.totalResults == null) -> NetworkErrorType.UnknownServerError
			//(response.status == null) -> NetworkErrorType.UnknownServerError
			else -> null
		}
	}
}