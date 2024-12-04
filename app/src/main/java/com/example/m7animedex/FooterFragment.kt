package com.example.m7animedex

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

class FooterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_footer, container, false)

        // Encontramos los layouts de cada sección
        val layoutSearch: LinearLayout = view.findViewById(R.id.layoutSearch)
        val layoutHome: LinearLayout = view.findViewById(R.id.layoutHome)
        val layoutList: LinearLayout = view.findViewById(R.id.layoutList)

        /*
        // Configurar los listeners de clic para cada layout
        layoutSearch.setOnClickListener {
            navigateToSearchActivity()
        }
        */

        layoutHome.setOnClickListener {
            navigateToHomeActivity()
        }
        /*
        layoutList.setOnClickListener {
            navigateToListActivity()
        }
        */

        return view
    }

    /* private fun navigateToSearchActivity() {
        val intent = Intent(requireContext(), ::class.java)
        startActivity(intent)
    }
    */


    private fun navigateToHomeActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }
    /*
    private fun navigateToListActivity() {
        val intent = Intent(requireContext(), ListActivity::class.java)
        startActivity(intent)
    }
    */
}
