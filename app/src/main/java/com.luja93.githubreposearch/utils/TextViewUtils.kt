package com.luja93.githubreposearch.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * Created by lleopoldovic on 07/12/2019.
 */

object TextViewUtils {

    fun setForegroundSpan(
        context: Context,
        text: String,
        @ColorRes color: Int,
        start: Int,
        end: Int? = null
    ): SpannableString {
        val spannableString = SpannableString(text)
        spannableString.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(context, color)
            ),
            start, end ?: spannableString.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )

        return spannableString
    }

}