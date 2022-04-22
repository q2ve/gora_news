/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository

import io.realm.RealmObject

interface DatabaseInterface {
	fun <T: RealmObject> insertOrUpdate(
		inputObject: T,
		onSuccess: ((T) -> Unit)? = null,
		onError: (() -> Unit)?
	)
	
	fun <T: RealmObject> insertOrUpdate(
		inputObjects: List<T>,
		onSuccess: ((List<T>) -> Unit)? = null,
		onError: (() -> Unit)?
	)
	
	fun <T: RealmObject> copyFromRealm(
		id: String,
		onSuccess: ((T) -> Unit),
		onError: (() -> Unit)?
	)
	
	fun <T: RealmObject> copyFromRealm(
		onSuccess: ((List<T>) -> Unit),
		onError: (() -> Unit)?
	)
}