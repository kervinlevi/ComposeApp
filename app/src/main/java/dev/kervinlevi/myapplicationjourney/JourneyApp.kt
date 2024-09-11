package dev.kervinlevi.myapplicationjourney

import android.app.Application
import dev.kervinlevi.myapplicationjourney.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JourneyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@JourneyApp)
            modules(AppModule.module)
        }
    }
}
