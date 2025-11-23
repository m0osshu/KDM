package com.example.myautoo.data.remote.api

import com.example.myautoo.data.remote.dto.MarcaDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MarcaApi {
    @GET("api/v1/marcas")
    suspend fun getVehiculos(): List<MarcaDto>

    @GET("api/v1/marcas/{id}")
    suspend fun getVehiculoById(@Path("id") id: Int): MarcaDto
}