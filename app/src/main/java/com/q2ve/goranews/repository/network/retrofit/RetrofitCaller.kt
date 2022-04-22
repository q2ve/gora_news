/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network.retrofit

import retrofit2.Call
import retrofit2.Callback

class RetrofitCaller {
	/**
	 * Returns RetrofitApiRequest configured by RetrofitRequestBuilder.
	 */
	fun getRequest(): RetrofitApiRequests {
		return RetrofitRequestBuilder().buildRequest()
	}
	
	/**
	 * Enqueues call with selected callback.
	 */
	fun <T> enqueueCall(call: Call<T>, callback: Callback<T>) {
		call.enqueue(callback)
	}
}