/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network

import com.q2ve.goranews.R

enum class NetworkErrorType(private val defaultMessageResource: Int) {
	InvalidApiKey(R.string.an_error_occurred),
	UnknownServerError(R.string.server_error),
	BadRequest(R.string.an_error_occurred),
	Throttling(R.string.throttling),
	NoConnection(R.string.no_server_connection);
	
	fun getDefaultMessage(): Int = this.defaultMessageResource
}