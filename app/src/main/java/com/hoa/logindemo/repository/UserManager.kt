package com.hoa.logindemo.repository

import com.hoa.logindemo.model.User
import javax.inject.Inject

interface UserManager {
    /**
     * Store user information
     */
    fun storeUserAccessibility(user: User)
}

class UserManagerImpl @Inject constructor() : UserManager {

    override fun storeUserAccessibility(user: User) {
        // Just for demo
    }
}