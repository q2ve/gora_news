package com.q2ve.goranews.repository.database.realm.dataclasses

import com.q2ve.goranews.repository.database.dataclassesInterfaces.ItemArticleInterface
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmItemArticle(
	@PrimaryKey
	override var url: String? = null,
	override var source: RealmItemSource? = null,
	override var author: String? = null,
	override var title: String? = null,
	override var description: String? = null,
	override var urlToImage: String? = null,
	override var publishedAt: String? = null,
	override var content: String? = null
): RealmObject(), ItemArticleInterface