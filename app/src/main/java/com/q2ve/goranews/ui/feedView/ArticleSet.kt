/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.feedView

import android.graphics.Bitmap
import com.q2ve.goranews.repository.database.realm.dataclasses.RealmItemArticle

class ArticleSet (
	val article: RealmItemArticle,
	val image: Bitmap?
)