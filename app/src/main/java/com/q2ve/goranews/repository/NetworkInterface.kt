/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository

import com.q2ve.goranews.repository.database.dataclassesInterfaces.ItemArticleInterface
import com.q2ve.goranews.repository.network.NetworkErrorType
import com.q2ve.goranews.repository.network.apiRequestsParameters.GetNewsParameters

interface NetworkInterface {
	fun <T: ItemArticleInterface?> getNews(
		parameters: GetNewsParameters,
		onSuccess: ((List<T>?) -> Unit)?,
		onError: ((NetworkErrorType) -> Unit)?
	)
}