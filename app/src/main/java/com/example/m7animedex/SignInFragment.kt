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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referencias a los botones
        val signInButton = view.findViewById<Button>(R.id.sign_in_button)
        val logInButton = view.findViewById<Button>(R.id.log_in_button)

        // Botón Sign In -> Navega al Home (MainActivity con Fragment Home)
        signInButton.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        // Botón Log In -> Navega al LogInFragment dentro de la misma actividad
        logInButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LogInFragment())
                .addToBackStack(null) // Permite volver atrás con el botón de retroceso
                .commit()
        }
    }*/

    private lateinit var viewModel: SignInViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        val usernameInput = view.findViewById<EditText>(R.id.username_input)
        val emailInput = view.findViewById<EditText>(R.id.email_input)
        val passwordInput = view.findViewById<EditText>(R.id.password_input)
        val rePasswordInput = view.findViewById<EditText>(R.id.rePassword_input)

        val signInButton = view.findViewById<Button>(R.id.sign_in_button)
        /*signInButton.setOnClickListener {
            viewModel.validateUserName(usernameInput.text.toString())
            viewModel.validateEmail(emailInput.text.toString())
            viewModel.validatePassword(passwordInput.text.toString())
            viewModel.checkPasswordsMatch(passwordInput.text.toString(), rePasswordInput.text.toString())

            if (viewModel.validUserName.value == true &&
                viewModel.validEmail.value == true &&
                viewModel.validPassword.value == true &&
                viewModel.passwordsMatch.value == true) {
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }*/
    }

}
