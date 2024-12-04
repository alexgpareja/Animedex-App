package com.example.m7animedex

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentHeader : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_header, container, false)

        val logoImageView: ImageView = view.findViewById(R.id.imageView4)
        val titleTextView: TextView = view.findViewById(R.id.tvHeaderTitle)
        val account: View = view.findViewById(R.id.imageView)  // Contenedor que agrupe el logo y el nombre de la app

        // Configurar acción del logo para redirigir a MainActivity
        logoImageView.setOnClickListener {
            navigateToMainActivity()
        }

        // Configurar acción del texto para redirigir a MainActivity
        titleTextView.setOnClickListener {
            navigateToMainActivity()
        }

        // Configurar acción del account para redirigir a Preferencias
        account.setOnClickListener {
            navigateToPreferences()
        }

        return view
    }

    // Función para navegar a la MainActivity
    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }

    // Función para navegar a la PreferencesActivity
    private fun navigateToPreferences() {
        val intent = Intent(requireContext(), Preferencias::class.java)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentHeader().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
