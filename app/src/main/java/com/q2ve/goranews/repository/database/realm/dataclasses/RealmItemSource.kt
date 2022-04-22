package com.q2ve.goranews.repository.database.realm.dataclasses

import com.q2ve.goranews.repository.database.dataclassesInterfaces.ItemSourceInterface
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmItemSource(
	@PrimaryKey
	override var id: String? = null,
	override var name: String? = null
): RealmObject(), ItemSourceInterface