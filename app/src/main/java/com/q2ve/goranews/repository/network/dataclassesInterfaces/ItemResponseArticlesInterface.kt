package com.q2ve.goranews.repository.network.dataclassesInterfaces

import com.q2ve.goranews.repository.database.dataclassesInterfaces.ItemArticleInterface

interface ItemResponseArticlesInterface {
	val status: String?
	val totalResults: Int?
	val articles: List<ItemArticleInterface?>?
}