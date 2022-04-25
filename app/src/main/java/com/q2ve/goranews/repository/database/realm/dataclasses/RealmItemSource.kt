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
	var name: String? = null,
	var id: String? = null
): RealmObject(), ValidationInterface {
	override fun checkValidity() = if (name == null || name == "") null else this
}