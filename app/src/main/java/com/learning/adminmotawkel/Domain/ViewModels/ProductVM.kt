package com.learning.adminmotawkel.Domain.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.adminmotawkel.Core.Helpers.showLogs
import com.learning.adminmotawkel.Core.UiState
import com.learning.adminmotawkel.Domain.FirebaseOperations
import com.learning.adminmotawkel.Domain.models.product.Category
import com.learning.adminmotawkel.Domain.models.product.ItemModel
import com.learning.adminmotawkel.Domain.models.product.TradeMarksModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductVM @Inject constructor(
    private val firebaseOperations: FirebaseOperations
) : ViewModel() {

    private val _getSections = MutableStateFlow<UiState<List<Category>>>(UiState.Loading)
    val getSections: StateFlow<UiState<List<Category>>> get() = _getSections

    private val _addSections = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val addSections: StateFlow<UiState<Boolean>> get() = _addSections

    private val _getTradeMarks = MutableStateFlow<UiState<List<TradeMarksModel>>>(UiState.Loading)
    val getTradeMarks: StateFlow<UiState<List<TradeMarksModel>>> get() = _getTradeMarks

    private val _addTradeMarks = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val addTradeMarks: StateFlow<UiState<Boolean>> get() = _addTradeMarks



    private val _getItems = MutableStateFlow<UiState<List<ItemModel>>>(UiState.Loading)
    val getItems: StateFlow<UiState<List<ItemModel>>> get() =_getItems

    private val _addItems = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val addItems: StateFlow<UiState<Boolean>> get() = _addItems


    fun addSection(product: Category) {
        showLogs("addSectionVM")

        viewModelScope.launch(Dispatchers.IO) {
            _addSections.value = UiState.Loading
            try {
                val result = firebaseOperations.addProduct(product)
                if (result) {
                    val updatedList = firebaseOperations.getSections()
                    _getSections.value = UiState.Success(updatedList)
                }
                _addSections.value = UiState.Success(result)
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


    // TODO For Trade Marks



    fun addTradeMarks(tradeMarksModel: TradeMarksModel , categoryId : String ) {

        viewModelScope.launch(Dispatchers.IO) {
            _addTradeMarks.value = UiState.Loading
            try {
                val result = firebaseOperations.addTradeMarks(tradeMarksModel , categoryId)
                if (result) {
                    val updatedList = firebaseOperations.getTradeMarks(categoryId)
                    _getTradeMarks.value = UiState.Success(updatedList)
                }
                _addTradeMarks.value = UiState.Success(result)
            } catch (e: Exception) {
                _addTradeMarks.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }


    fun loadTradeMarks(categoryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _getTradeMarks.value = UiState.Loading

            try {
                _getTradeMarks.value = UiState.Loading
                val data = firebaseOperations.getTradeMarks(categoryId)
                _getTradeMarks.value = UiState.Success(data)
            } catch (e: Exception) {
                _getTradeMarks.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }




    // TODO For Items



    fun addItems(items: ItemModel, categoryId : String , tradeMarksId: String) {

        viewModelScope.launch(Dispatchers.IO) {
            _addItems.value = UiState.Loading
            try {
                val result = firebaseOperations.addItems(items , categoryId  , tradeMarksId)
                if (result) {
                    val updatedList = firebaseOperations.getItems(categoryId,tradeMarksId)
                    _getItems.value = UiState.Success(updatedList)
                }
                _addItems.value = UiState.Success(result)
            } catch (e: Exception) {
                _addItems.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }


    fun loadItems(categoryId  :String , tradeMarksID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _getItems.value = UiState.Loading

            try {
                _getItems.value = UiState.Loading
                val data = firebaseOperations.getItems(categoryId, tradeMarksID)
                _getItems.value = UiState.Success(data)
            } catch (e: Exception) {
                _getItems.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}