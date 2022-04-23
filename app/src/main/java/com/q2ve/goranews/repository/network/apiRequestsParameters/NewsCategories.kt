/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network.apiRequestsParameters

import com.q2ve.goranews.R

enum class NewsCategories(private val nameResource: Int) {
	Science(R.string.science),
	Entertainment(R.string.entertainment),
	Sports(R.string.sports),
	General(R.string.general),
	Health(R.string.health),
	Technology(R.string.technology),
	Business(R.string.business);
	
	fun getCategoryId(): String = this.name.lowercase()
	
	fun getCategoryNameResource(): Int = this.nameResource
}