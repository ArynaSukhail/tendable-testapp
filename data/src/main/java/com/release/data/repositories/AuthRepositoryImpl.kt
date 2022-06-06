package com.release.data.repositories

import com.release.data.model.SignInRequest
import com.release.data.model.SignUpRequest
import com.release.data.prefs.AppPrefs
import com.release.data.service.ApiService
import com.release.data.utils.NetworkCall
import com.release.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val appPrefs: AppPrefs,
    private val apiService: ApiService,
    private val networkCall: NetworkCall
) : AuthRepository {

    override suspend fun signin(
        email: String,
        password: String
    ) {
        val body = SignInRequest(email, password)
        withContext(Dispatchers.IO) {
            networkCall.apiCall {
                apiService.signin(body)
                appPrefs.setEnteredKey(true)
            }
        }
    }

    override suspend fun signUp(
        email: String,
        password: String
    ) {
        val body = SignUpRequest(email, password)
        withContext(Dispatchers.IO) {
            networkCall.apiCall {
                apiService.signUp(body)
                appPrefs.setEnteredKey(true)
            }
        }
    }

    override suspend fun getUserEnteredKey(): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext appPrefs.getEnteredKey()
        }
    }

    override suspend fun signout() {
        withContext(Dispatchers.IO) {
            appPrefs.setEnteredKey(false)
        }
    }
}
