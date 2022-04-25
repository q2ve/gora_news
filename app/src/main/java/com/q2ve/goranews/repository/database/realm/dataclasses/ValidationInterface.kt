/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.database.realm.dataclasses

interface ValidationInterface {
	/**
	 * Return null if object contains invalid values.
	 * Otherwise returns object itself.
	 */
	fun checkValidity(): ValidationInterface?
}