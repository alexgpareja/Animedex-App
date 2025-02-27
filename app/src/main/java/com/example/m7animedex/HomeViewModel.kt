import com.example.m7animedex.data.model.Anime

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.m7animedex.data.api.AnimeService
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class HomeViewModel(private val apiService: AnimeService) : ViewModel() {

    private val _topAiringAnime = MutableLiveData<List<Anime>>()
    val topAiringAnime: LiveData<List<Anime>> = _topAiringAnime

    init {
        fetchTopAiringAnime()
    }

    private fun fetchTopAiringAnime() {
        viewModelScope.launch {
            try {
                val response = apiService.getAiringAnime()
                if (response.isSuccessful) {
                    _topAiringAnime.postValue(response.body()) // Enviamos la lista de animes al LiveData
                } else {
                    Log.e("HomeViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching anime: ${e.message}")
            }
        }
    }
}
