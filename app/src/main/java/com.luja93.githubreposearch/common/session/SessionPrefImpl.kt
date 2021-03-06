package com.luja93.githubreposearch.common.session

import android.content.Context
import android.content.SharedPreferences
import com.luja93.githubreposearch.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by lleopoldovic on 13/09/2019.
 */

@Singleton
class SessionPrefImpl @Inject
constructor(context: Context) : Session {
    private val mSharedPreferences: SharedPreferences = context.getSharedPreferences(
        BuildConfig.APPLICATION_ID + "_session", Context.MODE_PRIVATE)

}
