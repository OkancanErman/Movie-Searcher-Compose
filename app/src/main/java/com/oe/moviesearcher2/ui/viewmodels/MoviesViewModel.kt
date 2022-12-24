package com.oe.moviesearcher2.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oe.moviesearcher2.network.MovieResponse
import com.oe.moviesearcher2.network.Search
import com.oe.moviesearcher2.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    val repository: MoviesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<Status>(Status.Empty)
    val uiState: StateFlow<Status> = _uiState


    private val _searchString = MutableStateFlow("Batman")
    val searchString = _searchString.asStateFlow()

    fun onSearchStringChange(searchString: String){
        _searchString.value = searchString
    }

    private val _movies = MutableStateFlow<List<Search>>(
        emptyList()
    )
    val movies = _movies.asStateFlow()

    private val _details = MutableStateFlow<List<MovieResponse>>(
        emptyList()
    )

    val details = _details.asStateFlow()

    private fun getDetails(){
        viewModelScope.launch(Dispatchers.IO){
            val mutableList = mutableListOf<MovieResponse>()
            try {
                _movies.value.forEach{
                    val movieResponse = repository.getMovie(it.imdbID!!)
                    mutableList.add(movieResponse.body()!!)
                }
                withContext(Dispatchers.Main) {
                    _details.value = mutableList
                    _uiState.value = Status.Success(null)
                }
            }catch (throwable: Throwable){
                withContext(Dispatchers.Main) {
                    _details.value = mutableList
                    _uiState.value = Status.Error(throwable.toString())
                }
            }
        }
    }


    fun onSearchButtonClick(){

        viewModelScope.launch(Dispatchers.IO){
            _uiState.value = Status.Loading(null)
            try {

                val response = repository.searchMovies(_searchString.value)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _movies.value = response.body()?.search!!
                        getDetails()
                    } else
                        _uiState.value = Status.Error("Error Code: ${response.code()}")
                }

            }catch (throwable: Throwable){
                withContext(Dispatchers.Main) {
                    _uiState.value = Status.Error(throwable.toString())
                    _movies.value = emptyList()
                    _details.value = emptyList()
                }
            }
        }

    }

    sealed class Status {
        data class Loading(val message: String?) : Status()
        data class Success(val message: String?) : Status()
        data class Error(val message: String?) : Status()
        object Empty : Status()
    }

}