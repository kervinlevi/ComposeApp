package dev.kervinlevi.myapplicationjourney.di

import dev.kervinlevi.myapplicationjourney.BuildConfig
import dev.kervinlevi.myapplicationjourney.data.JobRepository
import dev.kervinlevi.myapplicationjourney.data.JobService
import dev.kervinlevi.myapplicationjourney.domain.SheetsJobRepository
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFormScreenViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {

    val module = module {
        single<JobService> {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SHEETS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            retrofit.create(JobService::class.java)
        }

        single<JobRepository> { SheetsJobRepository(get()) }
        viewModel<UpdateFormScreenViewModel> { UpdateFormScreenViewModel(get()) }
    }
}
