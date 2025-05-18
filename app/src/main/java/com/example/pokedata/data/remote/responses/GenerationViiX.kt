package com.example.pokedata.data.remote.responses


import com.google.gson.annotations.SerializedName

data class GenerationViiX(
    @SerializedName("lets-go-pikachu-lets-go-eevee")
    val letsGoPikachuLetsGoEevee: LetsGoPikachuLetsGoEevee,
    @SerializedName("sun-moon")
    val sunMoon: SunMoon,
    @SerializedName("ultra-sun-ultra-moon")
    val ultraSunUltraMoon: UltraSunUltraMoonX
)