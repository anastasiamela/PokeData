package com.example.pokedata.data.remote.responses


import com.google.gson.annotations.SerializedName

data class Colosseum(
    @SerializedName("name_icon")
    val nameIcon: String
)