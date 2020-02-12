package com.luja93.githubreposearch.common.mvvm.basemodels

/**
 * Created by lleopoldovic on 25/10/2019.
 */

data class BaseError(
    val errorMessage: String = baseErrorMessage,
    val errorCode: Int = ResponseCodes.UNDEFINED.code
) {
    companion object {
        var baseErrorMessage: String = "Some error occurred.\nPlease try later."
        var localErrorMessage: String = "Local error occurred."
        var networkErrorMessage: String = "Network error, check your internet connection."
        var emptyErrorBodyMessage: String = "{\"message\":\"Empty error body\"}"
    }
}