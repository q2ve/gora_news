/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.database.realm.dataclasses

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class IndexItem (
	@PrimaryKey
	var id: String = UUID.randomUUID().toString(),
	var listName: String = "",
	var index: Int = 0,
	var indexedArticles: RealmItemArticle? = null
): RealmObject()