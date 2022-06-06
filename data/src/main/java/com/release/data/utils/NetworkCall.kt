package com.release.data.utils

import com.release.domain.utils.AppException
import javax.inject.Inject

class NetworkCall @Inject constructor() {

    suspend inline fun <T : Any> apiCall(crossinline block: suspend () -> T): T {
        return try {
            block.invoke()
        } catch (e: Exception) {
            throw AppException(e.message.toString())
        }
    }
}
