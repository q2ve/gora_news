/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.database.realm.dataclasses

import com.q2ve.goranews.repository.database.dataclassesInterfaces.ItemSource
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmItemSource(
	@PrimaryKey
	override var id: String? = null,
	override var name: String? = null
): RealmObject(), ItemSource