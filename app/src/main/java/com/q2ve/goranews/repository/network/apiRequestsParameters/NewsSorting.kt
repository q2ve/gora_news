/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network.apiRequestsParameters

enum class NewsSorting(private val key: String? = null) {
	Relevancy,
	Popularity,
	PublishedAt("publishedAt");
	
	fun getKey(): String = this.key ?: this.name.lowercase()
}