package com.example.m7animedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class FragmentHeader : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_header, container, false)

        // Referencia al avatar
        val avatarIcon = view.findViewById<ImageView>(R.id.avatar_icon)

        // Configurar el clic para abrir el SettingsFragment
        avatarIcon.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment())
                .addToBackStack(null) // Permite volver atrás con el botón de retroceso
                .commit()
        }

        return view
    }
}
