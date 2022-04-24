/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.ui.mainFeed

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.q2ve.goranews.databinding.ViewCategoryItemBinding


class MainFeedAdapter(
	private var categories: List<CategorySet>
): RecyclerView.Adapter<MainFeedAdapter.RecyclerItemHolder> () {
	
	class RecyclerItemHolder(
		viewBinding: ViewCategoryItemBinding
	): RecyclerView.ViewHolder(viewBinding.root) {
		var title: TextView = viewBinding.viewCategoryItemTitle
		var frame: FrameLayout = viewBinding.viewCategoryItemFrame
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemHolder {
		val binding = ViewCategoryItemBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)
		return RecyclerItemHolder(binding)
	}
	
	override fun onBindViewHolder(holder: RecyclerItemHolder, position: Int) {
		if (position < itemCount) {
			holder.title.text = categories[position].title
			val view = categories[position].view
			(view.parent as? ViewGroup)?.removeView(view)
			holder.frame.addView(view)
		}
	}
	
	override fun getItemCount(): Int = categories.size
}