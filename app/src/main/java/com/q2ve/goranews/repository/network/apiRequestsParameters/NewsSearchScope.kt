/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.network.apiRequestsParameters

enum class NewsSearchScope {
	Title,
	Description,
	Content;
	
	companion object {
		fun getCompositeKey(vararg scopes: NewsSearchScope): String {
			return buildString { scopes.forEach { this.append(it.getKey() + ",") } }
		}
	}
	
	fun getKey(): String = this.name.lowercase()
}