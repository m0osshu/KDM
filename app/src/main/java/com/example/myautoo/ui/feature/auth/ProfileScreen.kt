package com.example.myautoo.ui.feature.auth

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myautoo.R
import com.example.myautoo.navigation.Screens
import com.example.myautoo.ui.viewModel.AuthViewModel
import com.example.myautoo.ui.viewModel.UserPhotoViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    userPhotoViewModel: UserPhotoViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val photoUri by userPhotoViewModel.photoUri.collectAsState()

    val context = LocalContext.current
    var tempUri: Uri? = null // ← Declaración de espacio de memoria para guardar foto

    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            navController.navigate(Screens.LOGIN) {
                popUpTo(Screens.PROFILE) { inclusive = true }
            }
        }
    }

    // Launcher para galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            currentUser?.uid?.let { userId ->
                userPhotoViewModel.savePhoto(userId, uri.toString())
            }
        }
    }

    // Launcher para cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            tempUri?.let { uri ->
                currentUser?.uid?.let { userId: String ->
                    userPhotoViewModel.savePhoto(userId, uri.toString())
                }
            }
        }
    }

    // Permiso de cámara
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val tempFile = File(context.cacheDir, "profile_photo.jpg")
            tempUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                tempFile
            )
            cameraLauncher.launch(tempUri)
        } else {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    //El perfil estaba ordinario, lo ordené un poco
    Scaffold(
        topBar = {
            AuthHeader(
                title = "Perfil",
                backIconRes = R.drawable.back2,
                onBack = { navController.navigate(Screens.HOME) }
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            //Variable para el estilo de la foto del usuario
            val userModifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .border(
                    width = 2.dp,
                    color = Color.Gray,
                    shape = CircleShape
                )

            if (currentUser != null) {
                if (photoUri != null) {
                    AsyncImage(
                        model = photoUri,
                        contentDescription = "Foto de perfil",
                        modifier = userModifier //así me ahorro escribir el modifier
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = "Foto por defecto",
                        modifier = userModifier
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                //El correo del usuario, en algún momento lo borré sin querer, ahora vuelve
                Text(
                    text = currentUser!!.email ?: "",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                //Se alarga el espaciado, dividir los email y foto de los botones
                Spacer(modifier = Modifier.height(84.dp))

                //Cree una fila para juntar los dos botones relacionados a la foto del perfil
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(top = 24.dp)
                ){
                    Button(
                        onClick = { galleryLauncher.launch("image/*") },
                        modifier = Modifier
                            .height(44.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Foto Galería")
                    }

                    Button(
                        onClick = { cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA) },
                        modifier = Modifier
                            .height(44.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Foto Cámara")
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                //Acceso a la pantalla de admin para editar/agregar marcas
                Button(
                    onClick = { navController.navigate(Screens.ADMIN) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) {
                    Text("Panel de Marcas (Admin)")
                }

                Spacer(modifier = Modifier.height(32.dp))

                //Botón de cerrar sesión. el último y en rojo para distinguirlo
                Button(
                    onClick = {
                        authViewModel.signOut()
                        navController.navigate(Screens.LOGIN) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text("Cerrar Sesión", fontWeight = FontWeight.SemiBold)
                }

                //Acá quizá haría el bloque de pie de página pero no se me ocurre nha
            }
        }
    }
}



