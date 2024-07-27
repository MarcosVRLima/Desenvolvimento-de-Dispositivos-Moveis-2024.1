package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraApp()
        }
    }
}

@Composable
fun CalculadoraApp() {
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = number1,
            onValueChange = { number1 = it },
            label = { Text("Número 1") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = number2,
            onValueChange = { number2 = it },
            label = { Text("Número 2") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                val num1 = number1.toDoubleOrNull()
                val num2 = number2.toDoubleOrNull()
                if (num1 != null && num2 != null) {
                    result = (num1 + num2).toString()
                } else {
                    result = "Por favor, insira números válidos."
                }
            }) {
                Text("Somar")
            }

            Button(onClick = {
                val num1 = number1.toDoubleOrNull()
                val num2 = number2.toDoubleOrNull()
                if (num1 != null && num2 != null) {
                    result = (num1 - num2).toString()
                } else {
                    result = "Por favor, insira números válidos."
                }
            }) {
                Text("Subtrair")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                val num1 = number1.toDoubleOrNull()
                val num2 = number2.toDoubleOrNull()
                if (num1 != null && num2 != null) {
                    result = (num1 * num2).toString()
                } else {
                    result = "Por favor, insira números válidos."
                }
            }) {
                Text("Multiplicar")
            }

            Button(onClick = {
                val num1 = number1.toDoubleOrNull()
                val num2 = number2.toDoubleOrNull()
                if (num1 != null && num2 != null) {
                    if (num2 != 0.0) {
                        result = (num1 / num2).toString()
                    } else {
                        result = "Divisão por zero não é permitida."
                    }
                } else {
                    result = "Por favor, insira números válidos."
                }
            }) {
                Text("Dividir")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Resultado: $result",
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
