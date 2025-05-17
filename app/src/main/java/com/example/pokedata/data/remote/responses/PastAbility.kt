package com.example.pokedata.data.remote.responses


import com.google.gson.annotations.SerializedName

data class PastAbility(
    @SerializedName("abilities")
    val abilities: List<AbilityXX>,
    @SerializedName("generation")
    val generation: Generation
)