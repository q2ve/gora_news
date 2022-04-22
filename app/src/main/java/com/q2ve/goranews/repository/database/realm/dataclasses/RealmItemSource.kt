/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.database.realm.dataclasses

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmItemSource(
	@PrimaryKey
	var id: String? = null,
	var name: String? = null
): RealmObject()