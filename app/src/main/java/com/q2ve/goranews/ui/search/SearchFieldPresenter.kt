/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.search

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.q2ve.goranews.databinding.ViewSearchFieldBinding

class SearchFieldPresenter {
	fun placeSearchField(
		container: ViewGroup,
		inflater: LayoutInflater,
		onSearchCallback: (String) -> Unit,
		onSearchFieldLostFocusCallback: () -> Unit,
		searchThrottlingTimeout: Long = 300
	) {
		val searchBox = ViewSearchFieldBinding.inflate(
			inflater,
			container,
			false
		)
		
		val searchField = searchBox.fragmentSearchSearchField
		val cancelButton = searchBox.fragmentSearchCancelButton
		
		var throttledText: String
		fun sendThrottledCallback(text: String) {
			throttledText = text
			Handler(Looper.getMainLooper()).postDelayed({
				if (throttledText == text) onSearchCallback(text)
			}, searchThrottlingTimeout)
		}
		
		searchField.addTextChangedListener(object: TextWatcher {
			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				if (s != null) {
					sendThrottledCallback(s.toString())
					cancelButton.isVisible = true
					cancelButton.setOnClickListener { searchField.editableText.clear() }
					if (s.isEmpty()) cancelButton.isVisible = false
				}
			}
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
			override fun afterTextChanged(s: Editable?) { }
		})
		
		searchField.setOnFocusChangeListener { _, hasFocus ->
			if (!hasFocus) onSearchFieldLostFocusCallback()
		}
		
		container.addView(searchBox.root)
	}
}