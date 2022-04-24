/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.database.realm.dataclasses

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmItemArticle(
	@PrimaryKey
	var url: String? = null,
	var source: RealmItemSource? = null,
	var author: String? = null,
	var title: String? = null,
	var description: String? = null,
	var urlToImage: String? = null,
	var publishedAt: String? = null,
	var content: String? = null
): RealmObject(), ValidationInterface {
	override fun checkValidity(): RealmItemArticle? {
		val checklist = mutableListOf(url, title, urlToImage /*description, urlToImage*/)
		if (source?.checkValidity() == null) source = null
		return if (checklist.contains(null) || checklist.contains("")) null else this
	}
}