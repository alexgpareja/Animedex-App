package com.example.m7animedex

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento que contiene el BottomNavigationView
        val view = inflater.inflate(R.layout.fragment_bottom_navigation, container, false)

        // Referencia al BottomNavigationView
        val bottomNavigationView: BottomNavigationView = view.findViewById(R.id.bottom_navigation)

        // Configurar el listener para manejar las selecciones de los ítems del menú
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_search -> {
                    // Verificar si la actividad actual NO es SearchActivity
                    if (requireActivity() !is SearchActivity) {
                        // Crear un intent para iniciar SearchActivity
                        val intent = Intent(requireContext(), SearchActivity::class.java)
                        // Configurar flags para evitar acumular actividades en la pila
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        startActivity(intent)
                    }
                    true
                }
                R.id.nav_home -> {
                    // Verificar si la actividad actual NO es MainActivity
                    if (requireActivity() !is MainActivity) {
                        // Crear un intent para iniciar MainActivity
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        // Configurar flags para evitar acumular actividades en la pila
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        startActivity(intent)
                    }
                    true
                }
                R.id.nav_lists -> {
                    // Verificar si la actividad actual NO es ListasActivity
                    if (requireActivity() !is ListasActivity) {
                        // Crear un intent para iniciar ListasActivity
                        val intent = Intent(requireContext(), ListasActivity::class.java)
                        // Configurar flags para evitar acumular actividades en la pila
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        startActivity(intent)
                    }
                    true
                }
                else -> false
            }
        }

        // Devolver la vista inflada
        return view
    }
}
