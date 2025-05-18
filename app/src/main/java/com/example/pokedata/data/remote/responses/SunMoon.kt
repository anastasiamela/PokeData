package com.example.pokedata.data.remote.responses


import com.google.gson.annotations.SerializedName

data class SunMoon(
    @SerializedName("name_icon")
    val nameIcon: String
)