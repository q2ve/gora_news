/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository

import com.q2ve.goranews.helpers.Constants

class ApiKeyProvider {
	companion object {
		fun getApiKey(): String = Constants.serverApiKey
	}
}