package com.luja93.githubreposearch.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Created by lleopoldovic on 23/09/2019.
 */

object GsonUtil {

    val defaultGson: Gson by lazy { GsonBuilder().create() }

}
