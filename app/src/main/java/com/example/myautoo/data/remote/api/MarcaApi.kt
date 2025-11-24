package com.example.myautoo.data.remote.api

import com.example.myautoo.data.remote.dto.MarcaDto
import com.example.myautoo.data.remote.dto.MarcaRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface MarcaApi {
    @GET("api/v1/marcas")
    suspend fun getMarcas(): List<MarcaDto>

    @GET("api/v1/marcas/{id}")
    suspend fun getMarcaById(@Path("id") id: Int): MarcaDto

    // Crear nueva marca (POST)
    @POST("api/v1/marcas")
    suspend fun createMarca(@Body marca: MarcaRequest): MarcaDto

    // Actualizar parcialmente una marca (PATCH)
    @PATCH("api/v1/marcas/{id}")
    suspend fun updateMarca(
        @Path("id") id: Int,
        @Body marca: MarcaRequest
    ): MarcaDto

    @DELETE("api/v1/marcas/{id}")
    suspend fun deleteMarca(@Path("id") id: Int): MarcaDto
}
