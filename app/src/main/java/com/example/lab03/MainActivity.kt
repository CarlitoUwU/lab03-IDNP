package com.example.lab03

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(false) }

            TiendaOnlineApp(
                isDarkTheme = isDarkTheme,
                onThemeChange = { isDarkTheme = !isDarkTheme }
            )
        }
    }
}

@Composable
fun TiendaOnlineApp(isDarkTheme: Boolean, onThemeChange: () -> Unit) {
    val navController = rememberNavController()

    MaterialTheme(
        colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
    ) {
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(
                    onLogin = { correo ->
                        navController.navigate("main/$correo") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
            composable(
                "main/{correo}",
                arguments = listOf(navArgument("correo") { type = NavType.StringType })
            ) { backStackEntry ->
                val correo = backStackEntry.arguments?.getString("correo") ?: ""
                MainScreen(correo, onThemeChange)
            }
        }
    }
}

@Composable
fun MainScreen(correo: String, onThemeChange: () -> Unit) {
    val navController = rememberNavController()
    val items = listOf("home", "carrito", "perfil")

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentDestination = navController.currentDestination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        selected = currentDestination == screen,
                        onClick = {
                            navController.navigate(screen) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            when (screen) {
                                "home" -> Icon(Icons.Default.Home, contentDescription = "Home")
                                "carrito" -> Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                                "perfil" -> Icon(Icons.Default.Person, contentDescription = "Perfil")
                            }
                        },
                        label = {
                            Text(
                                text = when (screen) {
                                    "home" -> "Inicio"
                                    "carrito" -> "Carrito"
                                    "perfil" -> "Perfil"
                                    else -> ""
                                }
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(correo) }
            composable("carrito") { CarritoScreen() }
            composable("perfil") { PerfilScreen(correo, onThemeChange) }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLogin: (String) -> Unit) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    Scaffold { padding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isPortrait) {
                // Diseño vertical
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LoginContent(correo, contrasena, onLogin,
                        onCorreoChange = { correo = it },
                        onContrasenaChange = { contrasena = it })
                }
            } else {
                // Diseño horizontal (landscape)
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LoginContent(correo, contrasena, onLogin,
                        onCorreoChange = { correo = it },
                        onContrasenaChange = { contrasena = it })
                }
            }
        }
    }
}

@Composable
fun LoginContent(
    correo: String,
    contrasena: String,
    onLogin: (String) -> Unit,
    onCorreoChange: (String) -> Unit,
    onContrasenaChange: (String) -> Unit
) {
    Text("Bienvenido", fontSize = 28.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(20.dp))

    OutlinedTextField(
        value = correo,
        onValueChange = onCorreoChange,
        label = { Text("Correo") },
        placeholder = { Text("Ingresa tu correo") },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.9f)
    )

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedTextField(
        value = contrasena,
        onValueChange = onContrasenaChange,
        label = { Text("Contraseña") },
        placeholder = { Text("Ingresa tu contraseña") },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.9f)
    )

    Spacer(modifier = Modifier.height(20.dp))

    Button(
        onClick = { onLogin(correo) },
        modifier = Modifier.fillMaxWidth(0.9f)
    ) {
        Text("Acceder", fontSize = 16.sp)
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        "Al continuar, aceptas nuestros Términos y Condiciones y Política de Privacidad.",
        fontSize = 12.sp
    )
}


@Composable
fun HomeScreen(correo: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("¡Bienvenido, $correo!\nExplora nuestros productos.", fontSize = 20.sp)
    }
}

@Composable
fun CarritoScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Tu carrito está vacío 🛒", fontSize = 20.sp)
    }
}

/* ✅ NUEVO PERFIL CON CONFIGURACIÓN Y CAMBIO DE TEMA */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(correo: String, onThemeChange: () -> Unit) {
    var nombre by remember { mutableStateOf("Carlos Valdivia") }
    var telefono by remember { mutableStateOf("+51 987 654 321") }
    var direccion by remember { mutableStateOf("Av. Los Olivos 234, Arequipa, Perú") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil de Usuario", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { onThemeChange() }) {
                        Text("🌓")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen de perfil (puedes reemplazar con imagen real)
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(nombre, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(correo, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(20.dp))

            Divider()

            Spacer(modifier = Modifier.height(16.dp))

            ProfileInfoItem(icon = Icons.Default.Phone, label = "Teléfono", value = telefono)
            ProfileInfoItem(icon = Icons.Default.Home, label = "Dirección", value = direccion)
            ProfileInfoItem(icon = Icons.Default.Settings, label = "Configuración de Tema", value = "Tocar el ícono superior 🌗")

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { /* Lógica de cerrar sesión */ }) {
                Icon(Icons.Default.ExitToApp, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar Sesión")
            }
        }
    }
}

@Composable
fun ProfileInfoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(label, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Text(value, fontSize = 14.sp)
        }
    }
}
