package com.decagon.android.sq007

import com.decagon.android.sq007.PokemonDetails.Ability
import com.decagon.android.sq007.PokemonDetails.Move
import com.decagon.android.sq007.PokemonDetails.Stat

object PokemonHolder {
    var abilities: List<Ability>? = null
    var stats: List<Stat>? = null
    var moves: List<Move>? = null
    var position: Int = 0
    var name: String? = null
}
