package com.example.pokedata.data.remote.responses


import com.google.gson.annotations.SerializedName

data class GenerationVX(
    @SerializedName("black-2-white-2")
    val black2White2: Black2White2,
    @SerializedName("black-white")
    val blackWhite: Black2White2
)