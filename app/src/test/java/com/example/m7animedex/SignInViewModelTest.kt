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
    fun test_1_Nom_dusuari_buit() {
        viewModel.actualitzaDades("", "prova@gmail.com", "abc123", "abc123")
        viewModel.validarFormulari()
        assertEquals(false, viewModel.formulariValid.value)
    }

    @Test
    fun test_2_Nom_dusuari_amb_espais_en_blanc() {
        viewModel.actualitzaDades(" a b ", "prova@gmail.com", "abc123", "abc123")
        viewModel.validarFormulari()
        assertEquals(false, viewModel.formulariValid.value)
    }

    @Test
    fun test_3_Nom_dusuari_amb_caracters_especials() {
        viewModel.actualitzaDades("usuari@#!$", "prova@gmail.com", "abc123", "abc123")
        viewModel.validarFormulari()
        assertEquals(false, viewModel.formulariValid.value)
    }

    @Test
    fun test_4_Nom_dusuari_massa_curt() {
        viewModel.actualitzaDades("ab", "prova@gmail.com", "abc123", "abc123")
        viewModel.validarFormulari()
        assertEquals(false, viewModel.formulariValid.value)
    }

    @Test
    fun test_5_Nom_dusuari_massa_llarg() {
        viewModel.actualitzaDades("usuarisuperllargquenocapdeloketoca", "prova@gmail.com", "abc123", "abc123")
        viewModel.validarFormulari()
        assertEquals(false, viewModel.formulariValid.value)
    }
}
