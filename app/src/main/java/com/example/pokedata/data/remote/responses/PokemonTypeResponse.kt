package com.example.pokedata.data.remote.responses


import com.google.gson.annotations.SerializedName

data class PokemonTypeResponse(
    @SerializedName("damage_relations")
    val damageRelations: DamageRelations,
    @SerializedName("game_indices")
    val gameIndices: List<GameIndiceX>,
    @SerializedName("generation")
    val generation: GenerationXX,
    @SerializedName("id")
    val id: Int,
    @SerializedName("move_damage_class")
    val moveDamageClass: MoveDamageClass,
    @SerializedName("moves")
    val moves: List<MoveXX>,
    @SerializedName("name")
    val name: String,
    @SerializedName("names")
    val names: List<Name>,
    @SerializedName("past_damage_relations")
    val pastDamageRelations: List<PastDamageRelation>,
    @SerializedName("pokemon")
    val pokemon: List<Pokemon>,
    @SerializedName("sprites")
    val sprites: SpritesX
)