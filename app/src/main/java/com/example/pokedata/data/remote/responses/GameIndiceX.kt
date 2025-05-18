package com.example.pokedata.data.remote.responses


import com.google.gson.annotations.SerializedName

data class GameIndiceX(
    @SerializedName("game_index")
    val gameIndex: Int,
    @SerializedName("generation")
    val generation: GenerationXX
)