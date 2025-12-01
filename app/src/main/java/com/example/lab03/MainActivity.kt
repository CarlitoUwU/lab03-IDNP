package com.example.lab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private val viewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val testRequest = OneTimeWorkRequestBuilder<LogWorker>().build()
        WorkManager.getInstance(this).enqueue(testRequest)

        val workRequest =
            PeriodicWorkRequestBuilder<LogWorker>(5, TimeUnit.MINUTES)
                .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "logWorker",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )

        setContent {
            AppContent(viewModel = viewModel)
        }
    }
}

@Composable
fun AppContent(viewModel: ProductViewModel) {
    val navController = rememberNavController()

    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            NavHost(navController = navController, startDestination = "list") {
                composable("list") {
                    val products by viewModel.products.collectAsState()
                    ProductListScreen(
                        products = products,
                        onAddClick = { navController.navigate("add") },
                        onDelete = { product -> viewModel.removeProduct(product) },
                        onClearAll = { viewModel.clearAll() }
                    )
                }
                composable("add") {
                    AddProductScreen(
                        onSave = { name, desc, priceStr, category ->
                            val price = priceStr.toDoubleOrNull() ?: 0.0
                            viewModel.addProduct(name.trim(), desc.trim(), price, category.trim())
                            navController.popBackStack()
                        },
                        onCancel = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
}
