package com.example.m7animedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AnalyticsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento
        return inflater.inflate(R.layout.fragment_analytics, container, false)
    }

    private lateinit var userPreferences: UserPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreferences = UserPreferences(requireContext())

        val barChart: BarChart = view.findViewById(R.id.barChart)
        val pieChart: PieChart = view.findViewById(R.id.pieChart)

        lifecycleScope.launch {
            val animeVisits = userPreferences.getAnimeVisits().first()
            val stats = userPreferences.getStats().first()

            setupBarChart(barChart, animeVisits)
            setupPieChart(pieChart, stats)
        }
    }

    private fun setupBarChart(barChart: BarChart, animeVisits: Map<Int, Int>) {
        // Cambiar de Entry a BarEntry
        val entries = animeVisits.map { BarEntry(it.key.toFloat(), it.value.toFloat()) }
        val dataSet = BarDataSet(entries, "Animes más vistos")
        dataSet.setColors(*intArrayOf(android.graphics.Color.BLUE, android.graphics.Color.RED))
        val barData = BarData(dataSet)
        barChart.data = barData
        barChart.invalidate()
    }


    private fun setupPieChart(pieChart: PieChart, stats: Pair<Int, Int>) {
        val (total, favs) = stats
        val nonFavs = total - favs

        val entries = listOf(
            PieEntry(favs.toFloat(), "Añadidos a favoritos"),
            PieEntry(nonFavs.toFloat(), "No añadidos")
        )

        val dataSet = PieDataSet(entries, "Porcentaje de favoritos")
        dataSet.setColors(*intArrayOf(android.graphics.Color.GREEN, android.graphics.Color.GRAY))
        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.invalidate()
    }
}