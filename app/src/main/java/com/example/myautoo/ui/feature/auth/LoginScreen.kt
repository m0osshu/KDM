package com.example.myautoo.ui.feature.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myautoo.R
import com.example.myautoo.navigation.Screens
import com.example.myautoo.ui.viewModel.AuthResult
import com.example.myautoo.ui.viewModel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    val uiState by authViewModel.uiState.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    val authResult by authViewModel.authResult.collectAsState()

// Acá se maneja el resultado de autenticación
    LaunchedEffect(authResult) {
        when (authResult) {
            is AuthResult.Success -> {
                // Acá navega al perfil despues de iniciar sesion
                navController.navigate(Screens.PROFILE) {
                    popUpTo(Screens.LOGIN) { inclusive = true }
                }
                authViewModel.resetAuthResult()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            AuthHeader(
                title = "Login",
                backIconRes = R.drawable.back2,
                onBack = { navController.popBackStack() }
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido de Nuevo",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color.Black
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ingresa para continuar",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.DarkGray,
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { authViewModel.onEmailChanged(it) },
                label = { Text("Email", color = Color.Gray) },
                singleLine = true,
                isError = uiState.emailError != null,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.LightGray,
                    cursorColor = Color.Black
                )
            )
            if (uiState.emailError != null) {
                Text(
                    text = uiState.emailError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Password
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { authViewModel.onPasswordChanged(it) },
                label = { Text("Contraseña", color = Color.Gray) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                isError = uiState.passwordError != null,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.LightGray,
                    cursorColor = Color.Black
                )
            )
            if (uiState.passwordError != null) {
                Text(
                    text = uiState.passwordError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            // Mostrar error de autenticación is hay un problema
            when (val result = authResult) {
                is AuthResult.Error -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = result.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                else -> {}
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (isLoading) {
                CircularProgressIndicator(color = Color.Black)
            } else {
                Button(
                    onClick = { authViewModel.signIn() },
                    enabled = uiState.isFormValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        "Ingresar",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = {
                navController.navigate(Screens.REGISTER)
            }) {
                Text("¿No tienes una cuenta? Crea una cuenta", color = Color.DarkGray)
            }
        }
    }
}

