package com.example.lab03

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val repository = ProductRepository(db.productDao())

    val products = repository.getAllProducts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addProduct(name: String, description: String, price: Double, category: String) {
        val product = Product(
            name = name,
            description = description,
            price = price,
            category = category
        )
        viewModelScope.launch {
            repository.insert(product)
        }
    }

    fun removeProduct(product: Product) {
        viewModelScope.launch {
            repository.delete(product)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}
