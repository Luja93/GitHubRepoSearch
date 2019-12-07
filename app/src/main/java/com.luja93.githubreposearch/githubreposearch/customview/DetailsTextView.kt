package com.luja93.githubreposearch.githubreposearch.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.luja93.githubreposearch.R
import kotlinx.android.synthetic.main.item_details_text.view.*

class DetailsTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.item_details_text, this)
    }

    fun setDetails(fieldName: String, fieldValue: String) {
        detail_field_name_TV.text = fieldName
        detail_field_value_TV.text = fieldValue
    }
}