package id.neotica.notes.di

import id.neotica.notes.data.AuthRepositoryImpl
import id.neotica.notes.data.NoteRepositoryImpl
import id.neotica.notes.data.SessionManager
import id.neotica.notes.domain.NoteRepository
import id.neotica.notes.presentation.screen.NoteViewModel
import id.neotica.notes.presentation.screen.auth.login.LoginViewModel
import id.neotica.notes.presentation.screen.auth.profile.ProfileViewModel
import id.neotica.notes.presentation.screen.auth.register.RegisterViewModel
import id.neotica.notes.presentation.screen.detail.NoteDetailViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initializeKoin(config: KoinAppDeclaration? = null) = startKoin {
    config?.invoke(this)
    modules(
        ktorModule,
        repoModules,
        sessionModule,
        platformModule()
    )
}

val sessionModule = module {
    singleOf(::SessionManager)
}

val repoModules = module {
    singleOf(::NoteRepositoryImpl).bind(NoteRepository::class)
    singleOf(::AuthRepositoryImpl)

    viewModelOf(::NoteViewModel)
    viewModelOf(::NoteDetailViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::ProfileViewModel)
}

val ktorModule = module {
    single {
        ktorClient
    }
}

val ktorClient = HttpClient {
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