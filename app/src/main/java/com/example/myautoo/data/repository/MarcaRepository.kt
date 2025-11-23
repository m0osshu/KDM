package com.example.myautoo.data.repository

import com.example.myautoo.data.remote.api.RetrofitClient
import com.example.myautoo.data.remote.dto.MarcaDto

class MarcaRepository {

    suspend fun getMarcas(): List<MarcaDto> {
        return RetrofitClient.marcaApi.getVehiculos()
    }
}