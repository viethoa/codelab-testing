package com.hoa.logindemo.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("access_token")
    val accessToken: String? = null
)