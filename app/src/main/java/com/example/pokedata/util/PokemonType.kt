package com.example.pokedata.util

import androidx.compose.ui.graphics.Color
import com.example.pokedata.ui.theme.TypeDark
import com.example.pokedata.ui.theme.TypeDragon
import com.example.pokedata.ui.theme.TypeElectric
import com.example.pokedata.ui.theme.TypeFairy
import com.example.pokedata.ui.theme.TypeFire
import com.example.pokedata.ui.theme.TypeGhost
import com.example.pokedata.ui.theme.TypeGrass
import com.example.pokedata.ui.theme.TypePsychic
import com.example.pokedata.ui.theme.TypeSteel
import com.example.pokedata.ui.theme.TypeWater

enum class PokemonType(val typeName: String, val color: Color) {
    FIRE("fire", TypeFire),
    WATER("water", TypeWater),
    GRASS("grass", TypeGrass),
    ELECTRIC("electric", TypeElectric),
    DRAGON("dragon", TypeDragon),
    PSYCHIC("psychic", TypePsychic),
    GHOST("ghost", TypeGhost),
    DARK("dark", TypeDark),
    STEEL("steel", TypeSteel),
    FAIRY("fairy", TypeFairy);

    companion object {
        fun fromApiName(typeName: String): PokemonType? {
            return entries.find { it.typeName == typeName.lowercase() }
        }
    }
}
