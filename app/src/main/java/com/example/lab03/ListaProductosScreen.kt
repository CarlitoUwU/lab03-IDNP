package com.example.lab03

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val categoria: String
)

@Composable
fun ListaProductosScreen() {
    val productos = generarProductos()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "🛒 Catálogo de Productos",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(productos) { producto ->
                ProductoItem(producto)
            }
        }
    }
}

@Composable
fun ProductoItem(producto: Producto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = producto.nombre,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = "Categoría: ${producto.categoria}")
            Text(
                text = "Precio: S/ ${producto.precio}",
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "Código: ${producto.id}",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

fun generarProductos(): List<Producto> {
    return listOf(
        Producto(1, "Arroz Costeño 5kg", 24.90, "Granos"),
        Producto(2, "Aceite Primor 1L", 10.50, "Aceites"),
        Producto(3, "Leche Gloria 400ml", 4.30, "Lácteos"),
        Producto(4, "Fideos Don Vittorio 1kg", 6.40, "Pastas"),
        Producto(5, "Azúcar Rubia 1kg", 4.20, "Endulzantes"),
        Producto(6, "Café Altomayo 250g", 15.80, "Bebidas"),
        Producto(7, "Pan Bimbo Integral", 9.60, "Panadería"),
        Producto(8, "Galletas Casino", 3.50, "Snacks"),
        Producto(9, "Yogurt Gloria 1L", 9.20, "Lácteos"),
        Producto(10, "Detergente Ariel 1kg", 13.90, "Limpieza"),
        Producto(11, "Shampoo Sedal 340ml", 8.30, "Higiene"),
        Producto(12, "Atún Florida 170g", 5.40, "Enlatados"),
        Producto(13, "Cereal Angel 500g", 8.90, "Desayuno"),
        Producto(14, "Queso Andino 250g", 11.50, "Lácteos"),
        Producto(15, "Gaseosa Inca Kola 1.5L", 7.20, "Bebidas"),
        Producto(16, "Chocolate Sublime 30g", 2.50, "Dulces"),
        Producto(17, "Huevos de corral x12", 9.80, "Huevos"),
        Producto(18, "Mantequilla Gloria 200g", 10.20, "Lácteos"),
        Producto(19, "Agua San Luis 2.5L", 3.90, "Bebidas"),
        Producto(20, "Sal Marina 1kg", 2.80, "Condimentos"),
        Producto(21, "Papel Higiénico Elite x4", 6.70, "Higiene"),
        Producto(22, "Jabón Lux 125g", 1.50, "Higiene"),
        Producto(23, "Cereal Zucaritas 500g", 9.10, "Desayuno"),
        Producto(24, "Salsa de Tomate Maggi 200g", 3.20, "Condimentos"),
        Producto(25, "Harina de Trigo 1kg", 4.00, "Granos"),
        Producto(26, "Mayonesa Hellmann's 400g", 12.30, "Condimentos"),
        Producto(27, "Jugo de Naranja Del Valle 1L", 8.50, "Bebidas"),
        Producto(28, "Cereal Corn Flakes 500g", 8.70, "Desayuno"),
        Producto(29, "Sardinas Primor 125g", 4.60, "Enlatados"),
        Producto(30, "Cerveza Cusqueña 330ml", 5.00, "Bebidas")
    )
}
