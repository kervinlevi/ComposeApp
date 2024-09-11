package dev.kervinlevi.myapplicationjourney.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface JobService {

    @POST("exec")
    suspend fun postJobEntry(@Body entry: JobEntry): Response<EmptyResponse>
}