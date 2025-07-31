package id.neotica.notes

import android.app.Application
import id.neotica.notes.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        initializeKoin {
            androidContext(this@MyApp)
        }
    }
}