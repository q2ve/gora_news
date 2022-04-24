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

class MainActivity: AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		Realm.init(this)
		Navigator.configure(this.supportFragmentManager)
		Navigator.replaceFragment(
			FeedFragment(),
			R.id.activity_main_frame,
			NavigatorAnimation.FadingWithoutScaling
		)
	}
}