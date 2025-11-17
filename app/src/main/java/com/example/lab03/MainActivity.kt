package com.example.lab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var themePreferences: ThemePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        themePreferences = ThemePreferences(this)

        setContent {
            val isDarkMode by themePreferences.isDarkMode.collectAsState(initial = false)

            AppTheme(isDarkMode) {
                val navController = rememberNavController()
                AppNavigation(
                    navController = navController,
                    isDarkMode = isDarkMode,
                    onThemeChange = { enabled ->
                        lifecycleScope.launch {
                            themePreferences.saveDarkMode(enabled)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = "configuracion"
    ) {
        composable("configuracion") {
            PantallaConfiguracion(
                isDarkMode = isDarkMode,
                onThemeChange = onThemeChange,
                onContinue = { navController.navigate("catalogo") }
            )
        }
        composable("catalogo") {
            ListaProductosScreen()
        }
    }
}
