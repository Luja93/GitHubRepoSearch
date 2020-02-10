package com.luja93.githubreposearch.utils

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.luja93.githubreposearch.R
import com.luja93.githubreposearch.githubreposearch.model.Repo

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

    fun buildSortingBottomSheetDialog(
        activity: Activity,
        selected: Repo.Sorting,
        onSortingSelectedCallback: (sorting: Repo.Sorting) -> Unit
    ): BottomSheetDialog {
        with(activity) {
            val sheetView = layoutInflater.inflate(R.layout.sorting_bottom_sheet, null)

            val bottomSheetDialog = BottomSheetDialog(activity, R.style.BottomSheetDialogTheme)
            bottomSheetDialog.setContentView(sheetView)
            bottomSheetDialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

            val default_txt = sheetView.findViewById<TextView>(R.id.sorting_default_tv)
            val watchers_txt = sheetView.findViewById<TextView>(R.id.sorting_watchers_tv)
            val forks_txt = sheetView.findViewById<TextView>(R.id.sorting_forks_tv)
            val issues_txt = sheetView.findViewById<TextView>(R.id.sorting_issues_tv)

            val default_img = sheetView.findViewById<ImageView>(R.id.sorting_default_iv)
            val watchers_img = sheetView.findViewById<ImageView>(R.id.sorting_watchers_iv)
            val forks_img = sheetView.findViewById<ImageView>(R.id.sorting_forks_iv)
            val issues_img = sheetView.findViewById<ImageView>(R.id.sorting_issues_iv)

            default_txt.text = Repo.Sorting.Default.name
            watchers_txt.text = Repo.Sorting.Watchers.name
            forks_txt.text = Repo.Sorting.Forks.name
            issues_txt.text = Repo.Sorting.Issues.name

            val textIconMap: MutableMap<TextView, ImageView> = mutableMapOf(
                default_txt to default_img,
                watchers_txt to watchers_img,
                forks_txt to forks_img,
                issues_txt to issues_img
            )

            textIconMap.forEach { (tv, iv) ->
                if (tv.text == selected.name) {
                    tv.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent))
                    iv.imageTintList =
                        ContextCompat.getColorStateList(activity, R.color.colorAccent)
                } else {
                    tv.setTextColor(ContextCompat.getColor(activity, R.color.textColorPrimary))
                    iv.imageTintList =
                        ContextCompat.getColorStateList(activity, R.color.textColorPrimary)
                }
            }

            sheetView.findViewById<LinearLayout>(R.id.sorting_default)
                .setOnClickListener {
                    onSortingSelectedCallback(Repo.Sorting.Default)
                    bottomSheetDialog.dismiss()
                }
            sheetView.findViewById<LinearLayout>(R.id.sorting_watchers)
                .setOnClickListener {
                    onSortingSelectedCallback(Repo.Sorting.Watchers)
                    bottomSheetDialog.dismiss()
                }
            sheetView.findViewById<LinearLayout>(R.id.sorting_forks)
                .setOnClickListener {
                    onSortingSelectedCallback(Repo.Sorting.Forks)
                    bottomSheetDialog.dismiss()
                }
            sheetView.findViewById<LinearLayout>(R.id.sorting_issues)
                .setOnClickListener {
                    onSortingSelectedCallback(Repo.Sorting.Issues)
                    bottomSheetDialog.dismiss()
                }

            return bottomSheetDialog
        }
    }
}
