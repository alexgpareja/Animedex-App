package com.example.m7animedex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {

    private var _nomUsuari = ""
    private var _correu = ""
    private var _contrasenya = ""
    private var _contrasenya2 = ""

    private val _errorNomUsuari = MutableLiveData<String>()
    private val _errorCorreu = MutableLiveData<String>()
    private val _errorContrasenya = MutableLiveData<String>()
    private val _errorContrasenya2 = MutableLiveData<String>()
    private val _formulariValid = MutableLiveData<Boolean>()

    val errorNomUsuari: LiveData<String> = _errorNomUsuari
    val errorCorreu: LiveData<String> = _errorCorreu
    val errorContrasenya: LiveData<String> = _errorContrasenya
    val errorContrasenya2: LiveData<String> = _errorContrasenya2
    val formulariValid: LiveData<Boolean> = _formulariValid

    fun actualitzaDades(nom: String, correu: String, contra1: String, contra2: String) {
        _nomUsuari = nom
        _correu = correu
        _contrasenya = contra1
        _contrasenya2 = contra2
    }

    fun validarFormulari() {
        var valid = true

        // NOM
        if (_nomUsuari.isBlank()) {
            _errorNomUsuari.value = "El nom d'usuari és obligatori"
            valid = false
        } else if (!Regex("^[a-zA-Z0-9_-]{3,20}$").matches(_nomUsuari)) {
            _errorNomUsuari.value = "Nom d'usuari no vàlid"
            valid = false
        } else {
            _errorNomUsuari.value = ""
        }

        // CORREU
        if (_correu.isBlank()) {
            _errorCorreu.value = "El correu és obligatori"
            valid = false
        } else if (!Regex("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$").matches(_correu)) {
            _errorCorreu.value = "Format de correu invàlid"
            valid = false
        } else {
            _errorCorreu.value = ""
        }

        // CONTRASENYA
        val lletres = _contrasenya.any { it.isLetter() }
        val numeros = _contrasenya.any { it.isDigit() }

        if (_contrasenya.length < 6 || !lletres || !numeros || _contrasenya.contains(" ")) {
            _errorContrasenya.value = "Contrasenya massa dèbil"
            valid = false
        } else {
            _errorContrasenya.value = ""
        }

        // COINCIDÈNCIA
        if (_contrasenya != _contrasenya2) {
            _errorContrasenya2.value = "Les contrasenyes no coincideixen"
            valid = false
        } else {
            _errorContrasenya2.value = ""
        }

        _formulariValid.value = valid
    }
}
