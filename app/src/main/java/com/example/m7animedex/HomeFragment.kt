package com.example.m7animedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {
    private lateinit var topAiringRecyclerView: RecyclerView
    private lateinit var mostPopularRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar los RecyclerView
        topAiringRecyclerView = view.findViewById(R.id.topAiringGrid)
        mostPopularRecyclerView = view.findViewById(R.id.mostPopularGrid)

        topAiringRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        mostPopularRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        topAiringRecyclerView.adapter = AnimeAdapter(AnimeProvider.Animes)
        mostPopularRecyclerView.adapter = AnimeAdapter(AnimeProvider.Animes)
    }
}
