package com.hoa.logindemo.ui.login

import com.hoa.logindemo.repository.UserManager
import com.hoa.logindemo.repository.UserManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class LoginModule {

    @Binds
    abstract fun bindUserManager(userManager: UserManagerImpl): UserManager
}