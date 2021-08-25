package com.dev.gka.abda.utilities

import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.snackbar.Snackbar

object Helpers {
    fun playAnimation(lottieAnimationView: LottieAnimationView) {
        lottieAnimationView.visibility = View.VISIBLE
        lottieAnimationView.playAnimation()
    }

    fun cancelAnimation(lottieAnimationView: LottieAnimationView) {
        lottieAnimationView.visibility = View.GONE
        lottieAnimationView.cancelAnimation()
    }

    fun showSnack(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
            .show()
    }
}