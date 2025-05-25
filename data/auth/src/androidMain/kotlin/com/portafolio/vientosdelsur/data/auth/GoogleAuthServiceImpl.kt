package com.portafolio.vientosdelsur.data.auth

import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.auth.oauth.GoogleAuthError
import com.portafolio.vientosdelsur.domain.auth.oauth.GoogleAuthService
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

internal class GoogleAuthServiceImpl(
    private val context: Context,
    private val credentialManager: CredentialManager
) : GoogleAuthService {
    override suspend fun login(): EmptyResult<GoogleAuthError> {
        return try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(BuildConfig.GOOGLE_OAUTH_CLIENT_ID)
                .setAutoSelectEnabled(false)
                .setFilterByAuthorizedAccounts(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(context = context, request)
            val credential = result.credential
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken
                val authCredential = GoogleAuthProvider.getCredential(idToken, null)
                val fireBase = FirebaseAuth.getInstance()
                fireBase.signInWithCredential(authCredential).await()
                Result.Success(Unit)
            } else {
                Result.Error(GoogleAuthError.REMOTE)
            }
        } catch (e: NoCredentialException) {
            Result.Error(GoogleAuthError.NO_ACCOUNT_ON_DEVICE)
        } catch (e: GetCredentialException) {
            e.printStackTrace()
            Result.Error(GoogleAuthError.REMOTE)
        } catch (e: Exception) {
            Result.Error(GoogleAuthError.REMOTE)
        }
    }
}