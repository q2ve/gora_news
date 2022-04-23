/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network

import com.q2ve.goranews.helpers.Constants

interface ApiKeyProviderInterface {
	fun getApiKey(): String = Constants.serverApiKey
}