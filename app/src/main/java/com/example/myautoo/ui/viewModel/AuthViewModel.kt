package com.example.myautoo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _user = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val user: StateFlow<FirebaseUser?> = _user

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Nuevo: estado del formulario
    private val _uiState = MutableStateFlow(AuthUiState())
    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(cl|com)$")
    val uiState: StateFlow<AuthUiState> = _uiState

    fun onEmailChanged(email: String) {
        val error = if (!emailRegex.matches(email)) {
            "Email inválido. Debe terminar en .cl o .com"
        } else null

        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = error,
            isFormValid = error == null &&
                    _uiState.value.passwordError == null &&
                    _uiState.value.confirmPasswordError == null
        )
    }

    fun onPasswordChanged(password: String) {
        val error = if (password.length < 6) {
            "La contraseña debe tener al menos 6 caracteres"
        } else null

        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = error,
            isFormValid = error == null && _uiState.value.emailError == null
        )
    }

    fun onConfirmPasswordChanged(confirm: String) {
        val error = if (confirm != _uiState.value.password) {
            "Las contraseñas no coinciden"
        } else null

        _uiState.value = _uiState.value.copy(
            confirmPassword = confirm,
            confirmPasswordError = error,
            isFormValid = error == null &&
                    _uiState.value.passwordError == null &&
                    _uiState.value.emailError == null
        )
    }

    fun signIn() {
        val state = _uiState.value
        if (!state.isFormValid) {
            _error.value = "Formulario inválido"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                auth.signInWithEmailAndPassword(state.email, state.password).await()
                _user.value = auth.currentUser
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signUp() {
        val state = _uiState.value
        if (!state.isFormValid) {
            _error.value = "Formulario inválido"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                auth.createUserWithEmailAndPassword(state.email, state.password).await()
                _user.value = auth.currentUser
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signOut() {
        auth.signOut()
        _user.value = null
    }
}
