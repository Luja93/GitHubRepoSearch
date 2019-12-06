package com.luja93.githubreposearch.githubreposearch.repository.mock

import com.luja93.githubreposearch.common.data.local.AppDatabase
import com.luja93.githubreposearch.common.data.remote.ApiInterface
import com.luja93.githubreposearch.common.session.SessionPrefImpl
import javax.inject.Inject

/**
 * Created by lleopoldovic on 03/10/2019.
 */

class MockRepo @Inject constructor(
    private val database: AppDatabase,
    private val api: ApiInterface,
    private val session: SessionPrefImpl
) {



}