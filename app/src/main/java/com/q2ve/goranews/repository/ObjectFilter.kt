/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository

import com.q2ve.goranews.repository.database.realm.dataclasses.ValidationInterface
import java.util.*

class ObjectFilter: ObjectFilterInterface {
	/**
	 * Checks the list of objects with it's checking method.
	 */
	override fun <T : ValidationInterface> checkList(items: List<T>): List<T> {
		val output = LinkedList<T>()
		items.forEach { if (it.checkValidity() != null) output.add(it) }
		return output.toList()
	}
}