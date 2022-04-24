/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.q2ve.goranews.databinding.ActivityMainBinding
import com.q2ve.goranews.helpers.navigator.Navigator
import com.q2ve.goranews.helpers.navigator.NavigatorAnimation
import com.q2ve.goranews.ui.FeedFragment
import io.realm.Realm

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		Realm.init(this)
		Navigator.configure(this.supportFragmentManager)
	}
	
	override fun onStart() {
		super.onStart()
		Navigator.replaceFragment(
			FeedFragment(),
			R.id.activity_main_frame,
			NavigatorAnimation.FadingWithoutScaling
		)
	}
	
//	override fun onResume() {
//		super.onResume()
//
//		val test: RepositoryInterface = Repository()
//		test.getNews(NewsCategories.Technology, {
//			if (it.networkErrorType == null) Log.e("Downloaded! Error type", it.networkErrorType.toString())
//			else Log.e("Restored! Error type", it.networkErrorType.toString())
//			//testLogArticles(it.articles)
//		},{
//			Log.e("Fail! Error type", it.toString())
//		})
//	}
//
//	private fun testLogArticles(list: List<RealmItemArticle>) {
//		Log.e("Articles", "----------------------------")
//		list.forEach {
//			Log.e("Article", it.title ?: "Название - null")
//		}
//		Log.e("/Articles", "----------------------------")
//	}
}