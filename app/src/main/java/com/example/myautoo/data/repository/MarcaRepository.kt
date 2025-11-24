package com.example.myautoo.data.repository

import com.example.myautoo.data.remote.api.MarcaApi
import com.example.myautoo.data.remote.dto.MarcaDto
import com.example.myautoo.data.remote.dto.MarcaRequest

class MarcaRepository(
    private val api: MarcaApi
) {
    suspend fun getMarcas(): List<MarcaDto> = api.getMarcas()

    suspend fun getMarcaById(id: Int): MarcaDto = api.getMarcaById(id)

    suspend fun createMarca(nombre: String, imagenMarca: String): MarcaDto {
        val request = MarcaRequest(nombre = nombre, imagenMarca = imagenMarca)
        return api.createMarca(request)
    }

    suspend fun updateMarca(id: Int, nombre: String, imagenMarca: String): MarcaDto {
        val request = MarcaRequest(nombre = nombre, imagenMarca = imagenMarca)
        return api.updateMarca(id, request)
    }

    suspend fun deleteMarca(id: Int) {
        api.deleteMarca(id)
    }
}
