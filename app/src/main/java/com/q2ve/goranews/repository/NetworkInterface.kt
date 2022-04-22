/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository

import com.q2ve.goranews.repository.database.realm.dataclasses.RealmItemArticle
import com.q2ve.goranews.repository.network.NetworkErrorType
import com.q2ve.goranews.repository.network.apiRequestsParameters.NewsGettingParameters

interface NetworkInterface {
	fun getNews(
		parameters: NewsGettingParameters,
		onSuccess: ((List<RealmItemArticle>) -> Unit)?,
		onError: ((NetworkErrorType) -> Unit)?
	)
}