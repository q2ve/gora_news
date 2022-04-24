/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.helpers

private typealias Observer<T> = (T) -> Unit

class Observable<T>(value: T) {
	private val callbacks = mutableListOf<Pair<String?, Observer<T>>>()
	
	var value: T = value
	set(value) {
		field = value
		callbacks.forEach { it.second(value) }
	}
	
	fun subscribe(observer: Observer<T>, key: String? = null) {
		callbacks.add(Pair(key, observer))
	}
	
	fun unsubscribe(observer: Observer<T>) {
		callbacks.forEach { if (it.second == observer) callbacks.remove(it) }
	}
	
	fun unsubscribe(key: String) {
		callbacks.forEach { if (it.first == key) callbacks.remove(it) }
	}
	
	fun unsubscribeAll() {
		callbacks.clear()
	}
}