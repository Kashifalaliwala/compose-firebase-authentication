package com.firebaseauthentication

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebaseauthentication.utils.StringResources
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    init {
        auth = Firebase.auth
    }

    var email by mutableStateOf("")
        private set

    fun updateEmail(input: String) {
        email = input
    }

    var password by mutableStateOf("")
        private set

    fun updatePassword(input: String) {
        password = input
    }

    init {
        updateEmail("")
        updatePassword("")
    }

    var emailError by mutableStateOf<StringResources.StringResource?>(null)

    var passwordError by mutableStateOf<StringResources.StringResource?>(null)

    companion object {
        lateinit var auth: FirebaseAuth
    }

    private fun validateEmail(): Boolean {
        val emailValue = email.trim()
        var isValid = true

        if (emailValue.isBlank() || emailValue.isEmpty()) {
            emailError = StringResources.StringResource(R.string.email_cannot_empty)
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            emailError = StringResources.StringResource(R.string.wrong_email_format)
            isValid = false
        } else {
            emailError = null
        }

        return isValid
    }

    private fun validatePassword(): Boolean {
        val passwordValue = password.trim()
        var isValid = true

        if (passwordValue.isBlank() || passwordValue.isEmpty()) {
            passwordError = StringResources.StringResource(R.string.fill_password)
            isValid = false
        } else if (passwordValue.length < 6) {
            passwordError = StringResources.StringResource(R.string.password_six_charac)
            isValid = false
        } else {
            passwordError = null
        }


        return isValid
    }

    fun validateForm(onSuccess: () -> Unit) {
        if (validateEmail() && validatePassword()) {
            onSuccess()
        }
    }

    suspend fun signUpFirebaseUser(context: Context): AuthResult? {
        return try {
            val signUpResult = auth.createUserWithEmailAndPassword(email, password).await()
            signUpResult
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    e.message,
                    Toast.LENGTH_SHORT,
                ).show()
            }
            null
        }
    }

    /**
     * Sign in firebase user
     *
     * @param context
     * @return
     */
    suspend fun signInFirebaseUser(current: Context): AuthResult? {
        return try {
            val signUpResult = auth.signInWithEmailAndPassword(email, password).await()
            signUpResult
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    current,
                    e.message,
                    Toast.LENGTH_SHORT,
                ).show()
            }
            null
        }
    }

    /**
     * Logout firebase user
     *
     * @param current
     * @return
     */
    suspend fun logoutFirebaseUser(current: Context): Boolean? {
        return try {
            if (auth.currentUser != null) auth.signOut()
            true
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    current,
                    e.message,
                    Toast.LENGTH_SHORT,
                ).show()
            }
            false
        }
    }
}