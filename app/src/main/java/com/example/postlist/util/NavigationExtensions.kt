package com.example.postlist.util

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.example.postlist.R

fun NavController.navigateWithFade(
    directions: NavDirections,
    popUpTo: Int? = null,
    inclusive: Boolean = false
) {
    val options = NavOptions.Builder()
        .setEnterAnim(R.anim.fade_in)
        .setExitAnim(R.anim.fade_out)
        .setPopEnterAnim(R.anim.fade_in)
        .setPopExitAnim(R.anim.fade_out)
        .apply {
            popUpTo?.let { setPopUpTo(it, inclusive) }
        }
        .build()

    navigate(directions, options)
}
