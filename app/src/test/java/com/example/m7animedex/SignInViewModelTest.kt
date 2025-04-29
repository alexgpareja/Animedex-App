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

    /************************* Noms d'usuari ************************/

    @Test
    fun testNomUsuariValid() {
        val result = viewModel.validarNomUsuari("usuari_valid123")
        assertEquals("", result)
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


    /************************* Correu electrònic ************************/

    @Test
    fun testCorreuCorrecte() {
        val result = viewModel.validarCorreu("usuari@gmail.com")
        assertEquals("", result)
    }

    @Test
    fun testCorreuSenseArrova() {
        val result = viewModel.validarCorreu("usuari.gmail.com")
        assertEquals("Format de correu invàlid", result)
    }

    @Test
    fun testCorreuAmbDominiIncomplet() {
        val result = viewModel.validarCorreu("usuari@com")
        assertEquals("Format de correu invàlid", result)
    }

    @Test
    fun testCorreuAmbCaractersNoPermesos() {
        val result = viewModel.validarCorreu("usuari@domini!.com")
        assertEquals("Format de correu invàlid", result)
    }

    /************************* Contrasenya ************************/

    @Test
    fun testContrasenyaCorrecta() {
        val result = viewModel.validarContrasenya("abc12345")
        assertEquals("", result)
    }

    @Test
    fun testContrasenyaBuida() {
        val result = viewModel.validarContrasenya("")
        assertEquals("La contrasenya és obligatòria", result)
    }

    @Test
    fun testContrasenyaMassaCurta() {
        val result = viewModel.validarContrasenya("abc12")
        assertEquals("Contrasenya massa curta (8-16 caràcters)", result)
    }

    @Test
    fun testContrasenyaMassaLlarga() {
        val contrasenyaLlarga = "a".repeat(51)
        val result = viewModel.validarContrasenya(contrasenyaLlarga)
        assertEquals("Contrasenya massa llarga (8-16 caràcters)", result)
    }

    @Test
    fun testContrasenyaSenseNumeros() {
        val result = viewModel.validarContrasenya("abcdefghi")
        assertEquals("La contrasenya ha de contenir lletres i números", result)
    }

    @Test
    fun testContrasenyaAmbEspais() {
        val result = viewModel.validarContrasenya("abc 1234")
        assertEquals("La contrasenya no pot contenir espais", result)
    }

    // Comparar les contrasenyes
    @Test
    fun testContrasenyesDiferents() {
        val result = viewModel.validarCoincidenciaContra("abc123", "abc124")
        assertEquals("Les contrasenyes no coincideixen", result)
    }

    @Test
    fun testContrasenyesCoincideixen() {
        val result = viewModel.validarCoincidenciaContra("abc123", "abc123")
        assertEquals("", result)
    }

}
