/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network.apiRequestsParameters

enum class NewsLanguages(private val key: String? = null) {
	All(""), Ar, De, En, Es, Fr, He, It, Nl, No, Pt, Ru, Sv, Ud, Zh;
	
	fun getKey(): String = this.key ?: this.name.lowercase()
}