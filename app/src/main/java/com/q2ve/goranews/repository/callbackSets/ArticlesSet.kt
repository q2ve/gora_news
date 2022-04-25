/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.callbackSets

import com.q2ve.goranews.repository.database.realm.dataclasses.RealmItemArticle
import com.q2ve.goranews.repository.network.NetworkErrorType

data class ArticlesSet(
	val articles: List<RealmItemArticle>,
	val networkErrorType: NetworkErrorType?
)