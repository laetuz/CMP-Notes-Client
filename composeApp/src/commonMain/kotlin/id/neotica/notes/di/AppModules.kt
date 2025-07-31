package id.neotica.notes.di

import id.neotica.notes.data.NoteRepositoryImpl
import id.neotica.notes.presentation.NoteViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initializeKoin(config: KoinAppDeclaration? = null) = startKoin {
    config?.invoke(this)
    modules(ktorModule, repoModules)
}

val repoModules = module {
    single { NoteRepositoryImpl(get()) }
//    singleOf(NoteRepositoryImpl::class)
    viewModelOf(::NoteViewModel)
}

val ktorModule = module {
    single {
        ktorClient
    }
}

val ktorClient = HttpClient() {
    install(ContentNegotiation) {
        json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
        )
    }
}