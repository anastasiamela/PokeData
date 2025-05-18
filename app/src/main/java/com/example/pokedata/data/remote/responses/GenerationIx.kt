package com.example.pokedata.data.remote.responses


import com.google.gson.annotations.SerializedName

data class GenerationIx(
    @SerializedName("scarlet-violet")
    val scarletViolet: ScarletViolet
)