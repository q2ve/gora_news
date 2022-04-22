package com.q2ve.goranews.repository.database.dataclassesInterfaces

interface ItemArticleInterface {
	val source: ItemSourceInterface?
	val author: String?
	val title: String?
	val description: String?
	val url: String?
	val urlToImage: String?
	val publishedAt: String?
	val content: String?
}