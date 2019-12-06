package com.luja93.githubreposearch.utils

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import com.luja93.githubreposearch.R

/**
 * Created by lleopoldovic on 23/11/2019.
 */

object DialogUtil {

    fun buildLoaderDialog(activity: Activity, loaderText: String?): Dialog {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_loader)

        val textV = dialog.findViewById<TextView>(R.id.loader_text)

        if (loaderText != null) {
            textV.text = loaderText
        }

        val loaderImage = dialog.findViewById<ImageView>(R.id.loading_img)

        val pulseAnim = ScaleAnimation(
            1f, 1.2f, 1f, 1.2f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        pulseAnim.repeatCount = Animation.INFINITE
        pulseAnim.repeatMode = Animation.REVERSE
        pulseAnim.duration = 180

        dialog.setOnShowListener { loaderImage.startAnimation(pulseAnim) }

        dialog.setOnDismissListener { loaderImage.clearAnimation() }

        return dialog
    }

}
