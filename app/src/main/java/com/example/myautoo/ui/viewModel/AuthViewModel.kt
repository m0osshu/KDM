package com.example.myautoo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


sealed class AuthResult{
    object Success: AuthResult()
    data class Error(val message: String): AuthResult()
    object Loading: AuthResult()
    object Idle: AuthResult()
}

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    //Estado UI
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _authResult = MutableStateFlow<AuthResult>(AuthResult.Idle)
    val authResult: StateFlow<AuthResult> = _authResult

    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(cl|com)$")

    init{
        checkAuthState()
    }

    private fun checkAuthState(){
        _currentUser.value = auth.currentUser
    }

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
        if (!_uiState.value.isFormValid) {
            _authResult.value = AuthResult.Error("Formulario inválido")
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _authResult.value = AuthResult.Loading
            try {
                auth.signInWithEmailAndPassword(
                    _uiState.value.email,
                    _uiState.value.password
                ).await()

                _currentUser.value = auth.currentUser
                _authResult.value = AuthResult.Success
                // Resetear el estado del formulario
                _uiState.value = AuthUiState()
            } catch (e: Exception) {
                _authResult.value = AuthResult.Error(e.message ?: "Error al iniciar sesión")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signUp() {
        if (!_uiState.value.isFormValid) {
            _authResult.value = AuthResult.Error("Formulario inválido")
            return
        }

        if (_uiState.value.password != _uiState.value.confirmPassword) {
            _authResult.value = AuthResult.Error("Las contraseñas no coinciden")
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _authResult.value = AuthResult.Loading
            try {
                auth.createUserWithEmailAndPassword(
                    _uiState.value.email,
                    _uiState.value.password
                ).await()

                _currentUser.value = auth.currentUser
                _authResult.value = AuthResult.Success
                // Resetear el estado del formulario
                _uiState.value = AuthUiState()
            } catch (e: Exception) {
                _authResult.value = AuthResult.Error(e.message ?: "Error al registrar usuario")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signOut() {
        auth.signOut()
        _currentUser.value = null
        _authResult.value = AuthResult.Idle
        _uiState.value = AuthUiState()
    }

    fun resetAuthResult() {
        _authResult.value = AuthResult.Idle
    }
}
