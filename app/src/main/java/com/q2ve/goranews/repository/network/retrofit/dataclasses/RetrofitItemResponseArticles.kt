package com.q2ve.goranews.repository.network.retrofit.dataclasses

import com.q2ve.goranews.repository.database.dataclassesInterfaces.ItemArticleInterface
import com.q2ve.goranews.repository.network.dataclassesInterfaces.ItemResponseArticlesInterface

data class RetrofitItemResponseArticles(
	override val status: String?,
	override val totalResults: Int?,
	override val articles: List<ItemArticleInterface?>?
): ItemResponseArticlesInterface
