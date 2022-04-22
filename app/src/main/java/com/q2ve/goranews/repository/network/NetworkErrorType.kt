/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network

enum class NetworkErrorType {
	InvalidApiKey,
	UnknownServerError,
	BadRequest,
	Throttling,
	NoConnection
}