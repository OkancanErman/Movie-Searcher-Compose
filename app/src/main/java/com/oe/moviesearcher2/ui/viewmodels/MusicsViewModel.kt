package com.oe.moviesearcher2.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.oe.moviesearcher2.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicsViewModel @Inject constructor(
    val repository: MoviesRepository
) : ViewModel() {
}