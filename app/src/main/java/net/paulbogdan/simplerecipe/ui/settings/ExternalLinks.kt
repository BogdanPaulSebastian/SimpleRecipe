package net.paulbogdan.simplerecipe.ui.settings

import android.app.Activity
import android.content.Context
import com.google.android.play.core.review.ReviewManagerFactory

fun rateUs(context: Context) {
    val manager = ReviewManagerFactory.create(context)
    val request = manager.requestReviewFlow()
    request.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            task.result
        }
        val flow = manager.launchReviewFlow((context as Activity), task.result)
        flow.addOnCompleteListener {
        }
    }
}