/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.database.realm

import android.util.Log
import com.q2ve.goranews.helpers.Constants
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject

class RealmIO {
	val config: RealmConfiguration = RealmConfiguration.Builder()
		.name(Constants.realmName)
		.build()
	
	fun <T: RealmObject> insertOrUpdate(
		inputObject: T,
		onSuccess: ((T) -> Unit)? = null,
		onError: (() -> Unit)?
	) {
		var output: T? = null
		val realm = Realm.getInstance(config)
		realm.executeTransactionAsync(
			{ r: Realm ->
				val objectInRealm = r.copyToRealmOrUpdate(inputObject)
				output = r.copyFromRealm(objectInRealm)
			}, {
				if (output != null) onSuccess?.let { it(output!!) }
				else onError?.invoke()
			}, { t: Throwable ->
				Log.e("RealmIO.insertOrUpdate single", t.toString())
				onError?.invoke()
			}
		)
		realm.close()
	}
	
	fun <T: RealmObject> insertOrUpdate(
		inputObjects: List<T>,
		onSuccess: ((List<T>) -> Unit)? = null,
		onError: (() -> Unit)?
	) {
		var output: List<T> = emptyList()
		val realm = Realm.getInstance(config)
		realm.executeTransactionAsync(
			{ r: Realm ->
				val objectsInRealm = r.copyToRealmOrUpdate(inputObjects)
				output = r.copyFromRealm(objectsInRealm)
			}, {
				onSuccess?.let { it(output) }
			}, { t: Throwable ->
				Log.e("RealmIO.insertOrUpdate multi", t.toString())
				onError?.invoke()
			}
		)
		realm.close()
	}
	
	inline fun <reified T: RealmObject> copyFromRealm(
		id: String,
		noinline onSuccess: ((T) -> Unit),
		noinline onError: (() -> Unit)?
	) {
		var output: T? = null
		val realm = Realm.getInstance(config)
		realm.executeTransactionAsync(
			{ r: Realm ->
				val realmObject: T? = r.where(T::class.java).equalTo("_id", id).findFirst()
				output = if (realmObject == null) null
				else r.copyFromRealm(realmObject)
			}, {
				if (output != null) onSuccess(output!!)
				else onError?.invoke()
			}, { t: Throwable ->
				Log.e("RealmIO.copyFromRealm single", t.toString())
				onError?.invoke()
			}
		)
		realm.close()
	}
	
	inline fun <reified T: RealmObject> copyFromRealm(
		noinline onSuccess: ((List<T>) -> Unit),
		noinline onError: (() -> Unit)?
	) {
		var output: List<T> = emptyList()
		val realm = Realm.getInstance(config)
		realm.executeTransactionAsync(
			{ r: Realm ->
				val realmObjects: List<T> = r.where(T::class.java).findAll()
				output = r.copyFromRealm(realmObjects)
			}, {
				onSuccess(output)
			}, { t: Throwable ->
				Log.e("RealmIO.copyFromRealm multi", t.toString())
				onError?.invoke()
			}
		)
		realm.close()
	}
}