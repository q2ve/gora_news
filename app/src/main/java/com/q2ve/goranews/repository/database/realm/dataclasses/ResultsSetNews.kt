/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.repository.database.realm.dataclasses

import io.realm.Realm
import io.realm.RealmResults

data class ResultsSetNews (
	val list: RealmResults<IndexItem>,
	val realmInstance: Realm
)