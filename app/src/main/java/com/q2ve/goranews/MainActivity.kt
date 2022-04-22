/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.q2ve.goranews.repository.database.realm.RealmIO
import com.q2ve.goranews.repository.database.realm.dataclasses.RealmItemArticle
import com.q2ve.goranews.repository.network.apiRequestsParameters.NewsGettingParameters
import com.q2ve.goranews.repository.network.retrofit.RetrofitCalls
import io.realm.Realm

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		Realm.init(this)
	}
	
	override fun onResume() {
		super.onResume()
		
		val test = NewsGettingParameters(
			"ac0793eb091f4bde9622dae5dfad2202",
			"business",
			8
		)
		RetrofitCalls().getNews(
			test,
			{
				Log.e("Connection success", "logging articles....")
				RealmIO().insertOrUpdateWithIndexing<RealmItemArticle>("DICK", it, true)
				testLogArticles(it)
			},
			{
				Log.e("Connection failed!", it.toString())
				RealmIO().copyIndexedFromRealm<RealmItemArticle>("DICK",
					{ tst ->
						Log.e("Showing from database", "logging articles....")
						testLogArticles(tst)
					}
				)
			}
		)
	}
	
	private fun testLogArticles(list: List<RealmItemArticle>) {
		Log.e("Articles", "----------------------------")
		list.forEach {
			Log.e("Article", it.title ?: "Название - null")
		}
		Log.e("/Articles", "----------------------------")
	}
}