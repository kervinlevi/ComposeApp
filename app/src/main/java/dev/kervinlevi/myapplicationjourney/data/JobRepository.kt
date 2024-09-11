package dev.kervinlevi.myapplicationjourney.data

import retrofit2.Response

interface JobRepository {

    suspend fun saveJobEntry(jobEntry: JobEntry): Response<EmptyResponse>

}