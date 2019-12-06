package com.luja93.githubreposearch.common.mvvm.basemodels

/**
 * Created by lleopoldovic on 25/10/2019.
 */

data class BaseError(
    val errorMessage: String = "Ups.. Looks like an error occurred.\nPlease try later.",
    val errorCode: Int = ResponseCodes.UNDEFINED.code
) {
}