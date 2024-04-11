package com.hoa.logindemo.di

import com.hoa.logindemo.repository.RepositoryModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.mockwebserver.MockWebServer

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class TestRepositoryViewModule : RepositoryModule() {

    companion object {
        const val MOCK_SERVER_PORT = 8132
    }

    @Provides
    fun provideMockWebServer(): MockWebServer = MockWebServer()

    override fun getUrlBase() = "http://localhost:$MOCK_SERVER_PORT"
}