package dev.kervinlevi.myapplicationjourney.domain

import dev.kervinlevi.myapplicationjourney.data.EmptyResponse
import dev.kervinlevi.myapplicationjourney.data.JobEntry
import dev.kervinlevi.myapplicationjourney.data.JobRepository
import dev.kervinlevi.myapplicationjourney.data.JobService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class SheetsJobRepository(
    private val jobService: JobService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : JobRepository {

    override suspend fun saveJobEntry(jobEntry: JobEntry): Response<EmptyResponse> {
        return withContext(ioDispatcher) {
            jobService.postJobEntry(jobEntry)
        }
    }
}
