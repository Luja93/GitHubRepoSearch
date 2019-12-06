package com.luja93.githubreposearch.common.session

/**
 * Created by lleopoldovic on 13/09/2019.
 */

interface Session {

    var token: String

    fun checkPermissionAsked(permission: String): Boolean

    fun setPermissionAsked(permission: String)

}
