/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.q2ve.goranews.repository.Repository
import com.q2ve.goranews.repository.RepositoryInterface
import com.q2ve.goranews.repository.database.realm.dataclasses.RealmItemArticle
import com.q2ve.goranews.repository.network.apiRequestsParameters.NewsCategories
import io.realm.Realm

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		Realm.init(this)
	}
	
	override fun onResume() {
		super.onResume()
		
		val test: RepositoryInterface = Repository()
		test.getNews(NewsCategories.Technology, {
			if (it.networkErrorType == null) Log.e("Downloaded! Error type", it.networkErrorType.toString())
			else Log.e("Restored! Error type", it.networkErrorType.toString())
			testLogArticles(it.articles)
		},{
			Log.e("Fail! Error type", it.toString())
		})
	}
	
	private fun testLogArticles(list: List<RealmItemArticle>) {
		Log.e("Articles", "----------------------------")
		list.forEach {
			Log.e("Article", it.title ?: "Название - null")
		}
		Log.e("/Articles", "----------------------------")
	}
}