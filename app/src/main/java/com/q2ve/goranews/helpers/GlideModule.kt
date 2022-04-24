/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.helpers

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule


@GlideModule
class GlideModule: AppGlideModule() {
	//Setting limit of glide in-memory cache size
	override fun applyOptions(context: Context, builder: GlideBuilder) {
		super.applyOptions(context, builder)
		val memoryCacheSizeBytes = 1024 * 1024 * 360 // 360MB
		builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))
	}
}