/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network.apiRequestsParameters

enum class NewsCategories {
	Science,
	Entertainment,
	Sports,
	General,
	Health,
	Technology,
	Business;
	
	fun getCategoryName(): String = this.name.lowercase()
}