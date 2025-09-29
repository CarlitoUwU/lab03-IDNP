package com.example.lab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TiendaOnlineApp()
        }
    }
}

@Composable
fun TiendaOnlineApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLogin = { correo ->
                    navController.navigate("home/$correo")
                }
            )
        }
        composable(
            "home/{correo}",
            arguments = listOf(navArgument("correo") { type = NavType.StringType })
        ) { backStackEntry ->
            val correo = backStackEntry.arguments?.getString("correo") ?: ""
            HomeScreen(correo, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLogin: (String) -> Unit) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Bienvenido", fontSize = 28.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo") },
                placeholder = { Text("Ingresa tu correo") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                placeholder = { Text("Ingresa tu contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { onLogin(correo) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Acceder")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Al continuar, aceptas nuestros Términos y Condiciones y Política de Privacidad.",
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun HomeScreen(correo: String, navController: NavHostController) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¡Bienvenido a la Tienda Online!\nTu correo: $correo",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text("Cerrar sesión y volver")
            }
        }
    }
}
