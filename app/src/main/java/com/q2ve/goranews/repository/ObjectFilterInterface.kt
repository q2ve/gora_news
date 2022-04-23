/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository

import com.q2ve.goranews.repository.database.realm.dataclasses.ValidationInterface

interface ObjectFilterInterface {
	/**
	 * Checks the list of objects with it's checking method.
	 */
	fun <T: ValidationInterface> checkList(items: List<T>): List<T>
}