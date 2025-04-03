package com.example.m7animedex

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AnalyticsFragment : Fragment() {

    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_analytics, container, false)
    }

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
        val entries = animeVisits.map { BarEntry(it.key.toFloat(), it.value.toFloat()) }
        val dataSet = BarDataSet(entries, "Animes más vistos").apply {
            setColors(Color.BLUE, Color.RED)
            valueTextSize = 12f // Tamaño del texto en cada barra
            valueTextColor = Color.BLACK
        }

        val barData = BarData(dataSet).apply {
            barWidth = 0.9f
        }

        barChart.apply {
            data = barData
            description.isEnabled = false
            setFitBars(true)
            axisLeft.textSize = 12f
            xAxis.textSize = 12f
            legend.textSize = 12f
            legend.textColor = Color.BLACK
            invalidate()
        }
    }

    private fun setupPieChart(pieChart: PieChart, stats: Pair<Int, Int>) {
        val (total, favs) = stats
        val nonFavs = total - favs

        val entries = listOf(
            PieEntry(favs.toFloat(), "Añadidos a favoritos"),
            PieEntry(nonFavs.toFloat(), "No añadidos")
        )

        val dataSet = PieDataSet(entries, "Porcentaje de favoritos").apply {
            setColors(Color.GREEN, Color.GRAY)
            valueTextSize = 10f // Reducimos el tamaño del número de porcentaje
            valueTextColor = Color.BLACK
        }

        val pieData = PieData(dataSet)

        pieChart.apply {
            data = pieData
            setUsePercentValues(true)
            setEntryLabelTextSize(10f) // Tamaño de las etiquetas dentro del gráfico
            setEntryLabelColor(Color.BLACK) // Color negro para las etiquetas
            setCenterTextSize(16f) // Tamaño del texto en el centro del gráfico
            description.isEnabled = false

            // Configurar la leyenda más pequeña y sin saltos de línea
            legend.apply {
                textSize = 12f // Reducimos el tamaño del texto
                textColor = Color.BLACK
                formSize = 14f // Tamaño del cuadro de color
                formToTextSpace = 6f // Espacio entre el cuadro de color y el texto
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
                maxSizePercent = 0.5f // Reduce el espacio ocupado por la leyenda
            }

            invalidate()
        }
    }
}
