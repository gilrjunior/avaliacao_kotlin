package com.example.prova02_gilmarjr.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prova02_gilmarjr.db.CustomerDAO
import com.example.prova02_gilmarjr.models.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerViewModel:ViewModel() {

    private val _customers = MutableLiveData<List<Customer>>(emptyList())
    private var customerDAO = CustomerDAO()
    private val _selectedCustomer = MutableLiveData<Customer>()

    val customers: LiveData<List<Customer>> get() = _customers
    val selectedCustomer: LiveData<Customer> get() = _selectedCustomer

    init {
        get_all()
    }

    fun set_selected_customer(customer: Customer){
        _selectedCustomer.value = customer
    }

    fun create(customer: Customer){
        viewModelScope.launch(Dispatchers.IO){
            customerDAO.insertCustomer(customer)
            get_all()
        }

    }

    fun update(customer: Customer){
        viewModelScope.launch(Dispatchers.IO){
            customerDAO.updateCustomer(customer)
            get_all()
        }
    }

    fun get_all(){
        viewModelScope.launch(Dispatchers.IO){
            _customers.postValue(customerDAO.getAllCustomers())
        }
    }

    fun delete(cpf:String){
        viewModelScope.launch(Dispatchers.IO){
            customerDAO.deleteCustomer(cpf)
            get_all()
        }
    }

    fun search_customer(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if(text != ""){
                val listCustomer = customerDAO.getAllCustomers()

                val filteredList = listCustomer.filter {
                    it.name.contains(text, ignoreCase = true) || it.cpf.contains(text)
                }

                _customers.postValue(filteredList)
            }else{
                get_all()
            }
        }
    }

}