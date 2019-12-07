package com.luja93.githubreposearch.githubreposearch.customview

import android.content.Context
import android.text.SpannableString
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.luja93.githubreposearch.R
import com.luja93.githubreposearch.common.kotlin.loadUrl
import kotlinx.android.synthetic.main.item_details_header.view.*

class DetailsHeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.item_details_header, this)
    }

    fun setHeaderDetails(avatarUrl: String, title: SpannableString, desc1: String, desc2: String) {
        avatar_IV.loadUrl(avatarUrl, false)
        title_TV.text = title
        desc1_TV.text = desc1
        desc2_TV.text = desc2
    }
}