package com.example.pokedata.data.remote.responses


import com.google.gson.annotations.SerializedName

data class SpritesX(
    @SerializedName("generation-iii")
    val generationIii: GenerationIiiX,
    @SerializedName("generation-iv")
    val generationIv: GenerationIvX,
    @SerializedName("generation-ix")
    val generationIx: GenerationIx,
    @SerializedName("generation-v")
    val generationV: GenerationVX,
    @SerializedName("generation-vi")
    val generationVi: GenerationViX,
    @SerializedName("generation-vii")
    val generationVii: GenerationViiX,
    @SerializedName("generation-viii")
    val generationViii: GenerationViiiX
)