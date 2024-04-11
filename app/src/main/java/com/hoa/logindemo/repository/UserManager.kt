package com.hoa.logindemo.repository

import com.hoa.logindemo.model.User

interface UserManager {
    /**
     * Store user information
     */
    fun storeUserAccessibility(user: User)
}

class UserManagerImpl : UserManager {

    override fun storeUserAccessibility(user: User) {
        // Just for demo
    }
}