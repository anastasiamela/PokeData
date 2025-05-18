package com.example.pokedata.data.remote.responses


import com.google.gson.annotations.SerializedName

data class MoveDamageClass(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)