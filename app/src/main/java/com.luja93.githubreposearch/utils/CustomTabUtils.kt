package com.luja93.githubreposearch.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Created by lleopoldovic on 07/12/2019.
 */

object CustomTabUtils {

    fun openInBrowser(context: Context, url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

}