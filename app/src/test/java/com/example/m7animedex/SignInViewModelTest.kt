package com.example.m7animedex

import com.example.m7animedex.viewmodel.SignInViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SignInViewModelTest {

    private lateinit var viewModel: SignInViewModel

    @Before
    fun setup() {
        viewModel = SignInViewModel()
    }

    @Test
    fun testNomUsuariBuit() {
        val result = viewModel.validarNomUsuari("")
        assertEquals("El nom d'usuari és obligatori", result)
    }

    @Test
    fun testNomUsuariAmbEspais() {
        val result = viewModel.validarNomUsuari(" a b ")
        assertEquals("Nom d'usuari amb caràcters no permesos", result)
    }

    @Test
    fun testNomUsuariAmbCaractersEspecials() {
        val result = viewModel.validarNomUsuari("usuari@#!$")
        assertEquals("Nom d'usuari amb caràcters no permesos", result)
    }

    @Test
    fun testNomUsuariMassaCurt() {
        val result = viewModel.validarNomUsuari("ab")
        assertEquals("Ha de tenir almenys 3 caràcters", result)
    }

    @Test
    fun testNomUsuariMassaLlarg() {
        val result = viewModel.validarNomUsuari("usuarisuperllargquenocapdeloketoca")
        assertEquals("No pot tenir més de 20 caràcters", result)
    }
}
