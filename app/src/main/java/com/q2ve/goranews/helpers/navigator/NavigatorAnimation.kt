/*
 * Created by Denis Shishkin
 * github.com/q2ve
 * qwq2eq@gmail.com
 */

package com.q2ve.goranews.helpers.navigator

import com.q2ve.goranews.R

enum class NavigatorAnimation(
	val enterAnimation: Int,
	val exitAnimation: Int,
	val popEnterAnimation: Int,
	val popExitAnimation: Int
) {
	SlideLtR(
		R.animator.slide_in_right_enter,
		R.animator.slide_in_right_exit,
		R.animator.slide_in_left_enter,
		R.animator.slide_in_left_exit
	),
	SlideRtL(
		R.animator.slide_in_left_enter,
		R.animator.slide_in_left_exit,
		R.animator.slide_in_right_enter,
		R.animator.slide_in_right_exit
	),
	SlideUpBounce(
		R.animator.slide_up_bounce_enter,
		R.animator.slide_up_bounce_exit,
		R.animator.slide_down_bounce_enter,
		R.animator.slide_down_bounce_exit
	),
	SlideDownBounce(
		R.animator.slide_down_bounce_enter,
		R.animator.slide_down_bounce_exit,
		R.animator.slide_up_bounce_enter,
		R.animator.slide_up_bounce_exit
	),
	Fading(
		R.animator.fading_enter,
		R.animator.fading_exit,
		R.animator.fading_enter,
		R.animator.fading_exit
	),
	FadingSlow(
		R.animator.fading_slow_enter,
		R.animator.fading_slow_exit,
		R.animator.fading_slow_enter,
		R.animator.fading_slow_exit
	),
	FadingWithoutScaling(
		R.animator.fading_without_scaling_enter,
		R.animator.fading_without_scaling_exit,
		R.animator.fading_without_scaling_enter,
		R.animator.fading_without_scaling_exit
	)
}