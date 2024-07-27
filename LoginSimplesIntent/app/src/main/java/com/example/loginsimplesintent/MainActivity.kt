package com.example.loginsimplesintent

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Define the root composable function
            LoginApp()
        }
    }
}

@Composable
fun LoginApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("home/{username}") { backStackEntry ->
            HomeScreen(navController, backStackEntry.arguments?.getString("username"))
        }
    }
}

@Composable
fun LoginScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val validUsers = mapOf(
        "admin" to "admin",
        "guest" to "guest"
    ) // Lista de usuários preexistentes com novas credenciais

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (validUsers[username] == password) {
                navController.navigate("home/$username")
            } else {
                Toast.makeText(
                    context,
                    "Login inválido!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text("Login")
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController, username: String?) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bem-vindo, $username!",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Navigate back to the login screen
            navController.navigate("login") {
                // Clear the back stack to prevent navigating back to the home screen
                popUpTo("login") { inclusive = true }
            }
        }) {
            Text("Logoff")
        }
    }
}
