package com.hoa.logindemo.ui.login

import com.hoa.logindemo.repository.UserManager
import com.hoa.logindemo.repository.UserManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class LoginModule {

    @Provides
    fun provideUserManager(): UserManager = UserManagerImpl()
}