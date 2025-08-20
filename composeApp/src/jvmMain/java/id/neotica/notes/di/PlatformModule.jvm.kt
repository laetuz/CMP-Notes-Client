package id.neotica.notes.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import id.neotica.notes.data.createDataStore
import id.neotica.notes.data.dataStoreFileName
import org.koin.dsl.module
import java.io.File

actual fun platformModule() = module {
    single { createDataStore() }
}

fun createDataStore(): DataStore<Preferences> = createDataStore(
    producePath = {
        val file = File(System.getProperty("java.io.tmpdir"), dataStoreFileName)
        file.absolutePath
    }
)