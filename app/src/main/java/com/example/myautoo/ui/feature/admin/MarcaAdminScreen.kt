package com.example.myautoo.ui.feature.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myautoo.data.remote.dto.MarcaDto
import com.example.myautoo.ui.viewModel.MarcaAdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarcaAdminScreen(
    viewModel: MarcaAdminViewModel,
    onBackClick: () -> Unit
) {
    val marcas by viewModel.marcas.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var marcaToEdit by remember { mutableStateOf<MarcaDto?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Marcas") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retroceder"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                marcaToEdit = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Marca")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(marcas) { marca ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(marca.nombre, modifier = Modifier.weight(1f))
                    Row {
                        IconButton(onClick = {
                            marcaToEdit = marca
                            showDialog = true
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                        }
                        IconButton(onClick = { viewModel.deleteMarca(marca.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
                Divider()
            }
        }

        if (showDialog) {
            MarcaFormDialog(
                marcaToEdit = marcaToEdit,
                onDismiss = { showDialog = false },
                onSave = { id, nombre, imagenUrl ->
                    if (id == null) {
                        viewModel.createMarca(nombre, imagenUrl)
                    } else {
                        viewModel.updateMarca(id, nombre, imagenUrl)
                    }
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun MarcaFormDialog(
    marcaToEdit: MarcaDto?,
    onDismiss: () -> Unit,
    onSave: (id: Int?, nombre: String, imagenUrl: String) -> Unit
) {
    var nombre by remember { mutableStateOf(marcaToEdit?.nombre ?: "") }
    var imagenUrl by remember { mutableStateOf(marcaToEdit?.imagenMarca ?: "") }

    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (marcaToEdit == null) "Crear Marca" else "Editar Marca",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre de la Marca") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = imagenUrl,
                    onValueChange = { imagenUrl = it },
                    label = { Text("URL de la Imagen") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onSave(marcaToEdit?.id, nombre, imagenUrl) }) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}