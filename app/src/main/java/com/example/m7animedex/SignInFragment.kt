package com.example.m7animedex

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.m7animedex.viewmodel.SignInViewModel

class SignInFragment : Fragment() {

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        // Referencias a los campos de entrada
        val usernameInput = view.findViewById<EditText>(R.id.username_input)
        val emailInput = view.findViewById<EditText>(R.id.email_input)
        val passwordInput = view.findViewById<EditText>(R.id.password_input)
        val rePasswordInput = view.findViewById<EditText>(R.id.rePassword_input)

        // Referencia al botón de Sign In
        val signInButton = view.findViewById<Button>(R.id.sign_in_button)

        signInButton.setOnClickListener {
            // Actualizar datos en el ViewModel
            viewModel.actualitzaNomUsuari(usernameInput.text.toString())
            viewModel.actualitzaCorreu(emailInput.text.toString())
            viewModel.actualitzaContrasenya(passwordInput.text.toString())
            viewModel.actualitzaContrasenya2(rePasswordInput.text.toString())

            // Realizar la validación en el ViewModel
            viewModel.comprovaDadesUsuari()

            // Establecer los errores en los campos de texto
            usernameInput.error = viewModel.errorNomUsuari.value.takeIf { it?.isNotEmpty() == true }
            emailInput.error = viewModel.errorCorreu.value.takeIf { it?.isNotEmpty() == true }
            passwordInput.error = viewModel.errorContrasenya.value.takeIf { it?.isNotEmpty() == true }
            rePasswordInput.error = viewModel.errorContrasenya2.value.takeIf { it?.isNotEmpty() == true }

            // Si el formulario es válido, navegar al siguiente fragmento o actividad
            if (viewModel.formulariValid.value == true) {
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }
}
