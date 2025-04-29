package com.example.m7animedex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {

    // Variables internes
    private var _nomUsuari: String = ""
    private var _correu: String = ""
    private var _contrasenya: String = ""
    private var _contrasenya2: String = ""

    // LiveData d’errors
    private val _errorNomUsuari = MutableLiveData<String>("")
    private val _errorCorreu = MutableLiveData<String>("")
    private val _errorContrasenya = MutableLiveData<String>("")
    private val _errorContrasenya2 = MutableLiveData<String>("")
    private val _formulariValid = MutableLiveData<Boolean>(false)

    // Accés públic
    val errorNomUsuari: LiveData<String> = _errorNomUsuari
    val errorCorreu: LiveData<String> = _errorCorreu
    val errorContrasenya: LiveData<String> = _errorContrasenya
    val errorContrasenya2: LiveData<String> = _errorContrasenya2
    val formulariValid: LiveData<Boolean> = _formulariValid

    // Funcions d’actualització
    fun actualitzaNomUsuari(nou: String) {
        _nomUsuari = nou
    }

    fun actualitzaCorreu(nou: String) {
        _correu = nou
    }

    fun actualitzaContrasenya(nou: String) {
        _contrasenya = nou
    }

    fun actualitzaContrasenya2(nou: String) {
        _contrasenya2 = nou
    }

    // Funcions de validació individuals
    fun validarNomUsuari(nom: String): String {
        return when {
            nom.isBlank() -> "El nom d'usuari és obligatori"
            nom.length < 3 -> "Ha de tenir almenys 3 caràcters"
            nom.length > 20 -> "No pot tenir més de 20 caràcters"
            !Regex("^[a-zA-Z0-9_-]+$").matches(nom) -> "Nom d'usuari amb caràcters no permesos"
            else -> ""
        }
    }

    fun validarCorreu(correu: String): String {
        return when {
            correu.isBlank() -> "El correu és obligatori"
            !Regex("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$").matches(correu) -> "Format de correu invàlid"
            else -> ""
        }
    }

    fun validarContrasenya(contra: String): String {
        val lletres = contra.any { it.isLetter() }
        val numeros = contra.any { it.isDigit() }

        return when {
            contra.isBlank() -> "La contrasenya és obligatòria"
            contra.length < 8 -> "Contrasenya massa curta (8-16 caràcters)"
            contra.length > 16 -> "Contrasenya massa llarga (8-16 caràcters)"
            contra.contains(" ") -> "La contrasenya no pot contenir espais"
            !lletres || !numeros -> "La contrasenya ha de contenir lletres i números"
            else -> ""
        }
    }

    fun validarCoincidenciaContra(c1: String, c2: String): String {
        return if (c1 != c2) "Les contrasenyes no coincideixen" else ""
    }

    // Funció final per comprovar tot el formulari
    fun comprovaDadesUsuari() {
        _errorNomUsuari.value = validarNomUsuari(_nomUsuari)
        _errorCorreu.value = validarCorreu(_correu)
        _errorContrasenya.value = validarContrasenya(_contrasenya)
        _errorContrasenya2.value = validarCoincidenciaContra(_contrasenya, _contrasenya2)

        _formulariValid.value = listOf(
            _errorNomUsuari.value,
            _errorCorreu.value,
            _errorContrasenya.value,
            _errorContrasenya2.value
        ).all { it.isNullOrBlank() }
    }
}
