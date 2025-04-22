package com.example.m7animedex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {

    private val _validUserName = MutableLiveData<Boolean>()
    val validUserName: LiveData<Boolean> get() = _validUserName

    private val _validEmail = MutableLiveData<Boolean>()
    val validEmail: LiveData<Boolean> get() = _validEmail

    private val _validPassword = MutableLiveData<Boolean>()
    val validPassword: LiveData<Boolean> get() = _validPassword

    private val _passwordsMatch = MutableLiveData<Boolean>()
    val passwordsMatch: LiveData<Boolean> get() = _passwordsMatch

    fun validateUserName(name: String) {
        _validUserName.value = name.length in 3..20 &&
                name.matches(Regex("^[a-zA-Z0-9_-]+$"))
    }

    fun validateEmail(email: String) {
        _validEmail.value = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && !email.contains("!") && !email.contains(" ")
                && email.contains(".") && email.split(".").last().length > 1
    }

    fun validatePassword(password: String) {
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        _validPassword.value = password.length in 6..50 &&
                hasLetter && hasDigit &&
                !password.contains(" ")
    }

    fun checkPasswordsMatch(pass1: String, pass2: String) {
        _passwordsMatch.value = pass1 == pass2
    }
}