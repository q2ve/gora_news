/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository

import com.q2ve.goranews.repository.database.dataclassesInterfaces.ItemArticle
import com.q2ve.goranews.repository.network.NetworkErrorType
import com.q2ve.goranews.repository.network.apiRequestsParameters.NewsGettingParameters

interface NetworkInterface {
	fun <T: ItemArticle?> getNews(
		parameters: NewsGettingParameters,
		onSuccess: ((List<T>?) -> Unit)?,
		onError: ((NetworkErrorType) -> Unit)?
	)
}