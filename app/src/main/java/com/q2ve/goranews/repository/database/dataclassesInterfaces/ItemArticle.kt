/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.database.dataclassesInterfaces

interface ItemArticle {
	val source: ItemSource?
	val author: String?
	val title: String?
	val description: String?
	val url: String?
	val urlToImage: String?
	val publishedAt: String?
	val content: String?
}