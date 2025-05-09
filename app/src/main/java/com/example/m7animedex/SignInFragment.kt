package com.example.m7animedex

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.m7animedex.viewmodel.SignInViewModel

class SignInFragment : Fragment() {

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        val usernameInput = view.findViewById<EditText>(R.id.username_input)
        val emailInput = view.findViewById<EditText>(R.id.email_input)
        val passwordInput = view.findViewById<EditText>(R.id.password_input)
        val rePasswordInput = view.findViewById<EditText>(R.id.rePassword_input)
        val signInButton = view.findViewById<Button>(R.id.sign_in_button)

        // Observar els errors en temps real
        viewModel.errorNomUsuari.observe(viewLifecycleOwner, Observer { error ->
            usernameInput.error = error.takeIf { it.isNotEmpty() }
        })
        viewModel.errorCorreu.observe(viewLifecycleOwner, Observer { error ->
            emailInput.error = error.takeIf { it.isNotEmpty() }
        })
        viewModel.errorContrasenya.observe(viewLifecycleOwner, Observer { error ->
            passwordInput.error = error.takeIf { it.isNotEmpty() }
        })
        viewModel.errorContrasenya2.observe(viewLifecycleOwner, Observer { error ->
            rePasswordInput.error = error.takeIf { it.isNotEmpty() }
        })

        signInButton.setOnClickListener {
            // Actualitzar dades al ViewModel
            viewModel.actualitzaNomUsuari(usernameInput.text.toString())
            viewModel.actualitzaCorreu(emailInput.text.toString())
            viewModel.actualitzaContrasenya(passwordInput.text.toString())
            viewModel.actualitzaContrasenya2(rePasswordInput.text.toString())

            // Validar dades
            viewModel.comprovaDadesUsuari()

            // Si el formulari és vàlid, navegar a HomeFragment
            if (viewModel.formulariValid.value == true) {
                // Substituir el fragment actual amb HomeFragment
                val homeFragment = HomeFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, homeFragment) // Assegurem que es substitueixi el fragment actual
                    .commit()
            } else {
                // Mostrar Toast si el formulari no és vàlid
                Toast.makeText(requireContext(), "Si us plau, corregeix els errors", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
