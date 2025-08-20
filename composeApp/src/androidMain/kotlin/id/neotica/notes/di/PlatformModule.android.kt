package id.neotica.notes.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import id.neotica.notes.data.createDataStore
import id.neotica.notes.data.dataStoreFileName
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        createDataStoreAndroid(get())
    }
}

fun createDataStoreAndroid(context: Context): DataStore<Preferences> = createDataStore(
    producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
)