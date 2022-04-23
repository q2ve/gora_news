/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.database.realm

import android.util.Log
import com.q2ve.goranews.helpers.Constants
import com.q2ve.goranews.repository.database.realm.dataclasses.IndexItem
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.Sort
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty

class RealmIO {
	val config: RealmConfiguration = RealmConfiguration.Builder()
		.name(Constants.realmName)
		.build()
	
	/**
	 * Inserts or updates object in the database.
	 */
	fun <T: RealmObject> insertOrUpdate(
		inputObject: T,
		onSuccess: ((T) -> Unit)? = null,
		onError: (() -> Unit)? = null
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
	
	/**
	 * Inserts or updates objects in the database.
	 */
	fun <T: RealmObject> insertOrUpdate(
		inputObjects: List<T>,
		onSuccess: ((List<T>) -> Unit)? = null,
		onError: (() -> Unit)? = null
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
	
	/**
	 * Copies object from the database by it's primary key.
	 */
	inline fun <reified T: RealmObject> copyFromRealm(
		id: String,
		noinline onSuccess: ((T) -> Unit),
		noinline onError: (() -> Unit)? = null
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
	
	/**
	 * Copies all objects of the provided class from the database.
	 */
	inline fun <reified T: RealmObject> copyFromRealm(
		noinline onSuccess: ((List<T>) -> Unit),
		noinline onError: (() -> Unit)? = null
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
	
	/**
	 * Deletes provided object from the database.
	 */
	fun <T: RealmObject> delete(
		inputObject: T,
		onSuccess: (() -> Unit)? = null,
		onError: (() -> Unit)? = null
	) {
		val realm = Realm.getInstance(config)
		realm.executeTransactionAsync(
			{ r: Realm ->
				val objectInRealm = r.copyToRealmOrUpdate(inputObject)
				objectInRealm.deleteFromRealm()
			}, { onSuccess?.let { it() } }, { t: Throwable ->
				Log.e("RealmIO.delete single", t.toString())
				onError?.invoke()
			}
		)
		realm.close()
	}
	
	/**
	 * Deletes all objects of the provided class from the database.
	 */
	inline fun <reified T: RealmObject> delete(
		noinline onSuccess: (() -> Unit)? = null,
		noinline onError: (() -> Unit)? = null
	) {
		val realm = Realm.getInstance(config)
		realm.executeTransactionAsync(
			{ r: Realm ->
				val objectsInRealm = r.where(T::class.java).findAll()
				objectsInRealm.deleteAllFromRealm()
			}, { onSuccess?.let { it() } }, { t: Throwable ->
				Log.e("RealmIO.delete multi", t.toString())
				onError?.invoke()
			}
		)
		realm.close()
	}
	
	/**
	 * This function is needed to index items we got from the server
	 * and then to adding them to the local database.
	 */
	inline fun <reified T: RealmObject> insertOrUpdateWithIndexing(
		listName: String,
		inputObjects: List<RealmObject>,
		clearOldList: Boolean,
		noinline onSuccess: (() -> Unit)? = null,
		noinline onError: (() -> Unit)? = null
	) {
		val realm = Realm.getInstance(config)
		realm.executeTransactionAsync(
			{ r: Realm ->
				if (clearOldList) {
					val oldIndexItems = r.where(IndexItem::class.java)
						.equalTo("listName", listName)
						.findAll()
					oldIndexItems.deleteAllFromRealm()
				}
				
				var lastIndex = 0
				r.where(IndexItem::class.java)
					.equalTo("listName", listName)
					.findAll()
					.forEach { if (it.index > lastIndex) lastIndex = it.index }
				inputObjects.forEach { realmObject ->
					r.copyToRealmOrUpdate(realmObject)
					/**
					 * Index items needs to get the objects from DB
					 * in the correct order they came from the server.
					 */
					val indexItem = IndexItem()
					indexItem.index = lastIndex + 1
					indexItem.listName = listName
					/**
					 * A Great Reflexive Magic that finds right field in the index item
					 * and fills it with the inputted object.
					 * It's necessary for this code to work with objects of any classes
					 * that i might add in the future.
					 * But for every new realm dataclass, that we want to index, we must
					 * add new field in the IndexItem.
					 */
					val fields = IndexItem::class.members
					val field = fields.find {
						it.returnType.classifier.toString() == T::class.java.toString()
					}
					if (field is KMutableProperty) {
						field.setter.call(indexItem, realmObject)
					}
					r.insertOrUpdate(indexItem)
					lastIndex++
				}
			}, { onSuccess?.invoke() }, { t: Throwable ->
				Log.e("RealmIO.insertOrUpdateWithIndexing", t.toString())
				onError?.invoke()
			}
		)
		realm.close()
	}
	
	/**
	 * Retrieves items from database in the order they were put there.
	 * Make sure your "T" matches class of the objects which stores
	 * in the list with name you specified.
	 */
	inline fun <reified T: RealmObject> copyIndexedFromRealm(
		listName: String,
		noinline onSuccess: (List<T>) -> Unit,
		noinline onError: (() -> Unit)? = null
	) {
		var output: List<T> = emptyList()
		val realm = Realm.getInstance(config)
		realm.executeTransactionAsync(
			{ r: Realm ->
				/**
				 * Sorting objects from DB with index items (also from DB).
				 */
				val indexObjectsSortedList = r.where(IndexItem::class.java)
					.equalTo("listName", listName)
					.sort("index", Sort.ASCENDING)
					.findAll()
				val sortedList: MutableList<T> = emptyList<T>().toMutableList()
				indexObjectsSortedList.forEach { indexItem ->
					val fields = IndexItem::class.members
					val field = fields.find {
						it.returnType.classifier.toString() == T::class.java.toString()
					}
					if (field is KProperty) {
						val item = field.getter.call(indexItem) as? T
						if (item != null) sortedList += item
					}
				}
				/**
				 * Copying objects from DB. Now they're not related to DB.
				 */
				output = r.copyFromRealm(sortedList)
			}, { onSuccess.invoke(output) }, { t: Throwable ->
				Log.e("RealmIO.copyIndexedFromRealm", t.toString())
				onError?.invoke()
			}
		)
		realm.close()
	}
	
//	fun getFilteredNewsResults(
//		category: NewsCategories,
//		onSuccess: (ResultsSetNews) -> Unit,
//		onError: (() -> Unit)? = null
//	) {
//		val realm = Realm.getInstance(config)
//		val test = realm.where(RealmItemArticle::class.java).equalTo("category", category)
//			.equalTo("isClosed", isClosed)
//			.lessThan("endDate", limitDate)
//			.sort("title", Sort.ASCENDING)
//			.findAll()
//		//return DeadlinesRealmResultsSet(list, realm)
//	}
}