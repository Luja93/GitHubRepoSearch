package com.luja93.githubreposearch.utils.view

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalOffsetDecoration(context: Context, offset: Float) : RecyclerView.ItemDecoration() {

    private val offset: Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            offset,
            context.resources.displayMetrics
        ).toInt()

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.top = offset / 2
        outRect.bottom = offset / 2
    }
}