package com.example.prova02_gilmarjr.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova02_gilmarjr.db.BikeDAO
import com.example.prova02_gilmarjr.models.Bike
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BikeViewModel: ViewModel() {

    private val _bikes = MutableLiveData<List<Bike>>(emptyList())
    private var bikeDAO = BikeDAO()
    private val _selectedBike = MutableLiveData<Bike>()

    val bikes: LiveData<List<Bike>> get() = _bikes
    val selectedBike: LiveData<Bike> get() = _selectedBike

    init {
        get_all()
    }

    fun set_selected_bike(bike: Bike) {
        _selectedBike.value = bike
    }

    fun create(bike: Bike) {
        viewModelScope.launch(Dispatchers.IO) {
            bikeDAO.insertBike(bike)
            get_all()
        }
    }

    fun update(bike: Bike) {
        viewModelScope.launch(Dispatchers.IO) {
            bikeDAO.updateBike(bike)
            get_all()
        }
    }

    fun get_all() {
        viewModelScope.launch(Dispatchers.IO) {
            _bikes.postValue(bikeDAO.getAllBikes())
        }
    }

    fun delete(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            bikeDAO.deleteBike(code)
            get_all()
        }
    }

    fun search_bike(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listBike = bikeDAO.getAllBikes()

            val filteredList = listBike.filter {
                it.model.contains(text, ignoreCase = true) || it.code.contains(text) || it.customer_cpf.contains(text)
            }

            _bikes.postValue(filteredList)
        }
    }

}