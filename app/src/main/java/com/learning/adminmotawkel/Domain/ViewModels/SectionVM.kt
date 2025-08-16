package com.learning.adminmotawkel.Domain.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.adminmotawkel.Core.UiState
import com.learning.adminmotawkel.Domain.FirebaseOperations
import com.learning.adminmotawkel.Domain.models.sections.SectionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SectionVM @Inject constructor(
    private val firebaseOperations: FirebaseOperations
) : ViewModel() {

    private val _getSections = MutableStateFlow<UiState<List<SectionModel>>>(UiState.Loading)
    val getSections: StateFlow<UiState<List<SectionModel>>> get() =_getSections

    private val _addSections = MutableStateFlow<UiState<SectionModel>>(UiState.Loading)
    val addSections: StateFlow<UiState<SectionModel>> get() =_addSections

    fun addSection(section: SectionModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _addSections.value = UiState.Loading
            try {
                firebaseOperations.addSection(section)
                _addSections.value = UiState.Success(section)
            } catch (e: Exception) {
                _addSections.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadSections() {
        viewModelScope.launch(Dispatchers.IO) {
            _getSections.value = UiState.Loading

            try {
                _getSections.value = UiState.Loading
                val data = firebaseOperations.getSections()
                _getSections.value = UiState.Success(data)
            } catch (e: Exception) {
                _getSections.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}