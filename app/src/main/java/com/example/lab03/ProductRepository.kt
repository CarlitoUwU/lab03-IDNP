package com.example.lab03

import kotlinx.coroutines.flow.Flow

class ProductRepository(private val dao: ProductDao) {

    fun getAllProducts(): Flow<List<Product>> = dao.getAllProducts()

    suspend fun insert(product: Product) = dao.insert(product)

    suspend fun delete(product: Product) = dao.delete(product)

    suspend fun deleteAll() = dao.deleteAll()
}
