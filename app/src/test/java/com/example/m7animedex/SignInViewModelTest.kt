package com.example.m7animedex

import com.example.m7animedex.viewmodel.SignInViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SignInViewModelTest {

    private lateinit var viewModel: SignInViewModel

    @Before
    fun setUp() {
        viewModel = SignInViewModel()
    }

    @Test
    fun `nom d'usuari buit retorna false`() {
        viewModel.validateUserName("")
        assertEquals(false, viewModel.validUserName.value)
    }

    @Test
    fun `correu amb format valid retorna true`() {
        viewModel.validateEmail("usuari@gmail.com")
        assertEquals(true, viewModel.validEmail.value)
    }

    @Test
    fun `contrasenyes coincideixen retorna true`() {
        viewModel.checkPasswordsMatch("abc123", "abc123")
        assertEquals(true, viewModel.passwordsMatch.value)
    }

    @Test
    fun `contrasenya massa llarga retorna false`() {
        val longPass = "a".repeat(51)
        viewModel.validatePassword(longPass)
        assertEquals(false, viewModel.validPassword.value)
    }

    // Pots afegir la resta aquí...
}