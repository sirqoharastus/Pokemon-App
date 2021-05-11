package com.decagon.android.sq007.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.PokemonDetails.Ability
import com.decagon.android.sq007.R

class PokemonAbilitiesAdapter(var pokemonAbilitiesList: List<Ability>) : RecyclerView.Adapter<PokemonAbilitiesAdapter.PokemonDetailsViewHolder>() {

    inner class PokemonDetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val abilityTextView: TextView = view.findViewById(R.id.details_textview)
        fun bind(statList: List<Ability>, position: Int) {
            abilityTextView.text = pokemonAbilitiesList[position].ability.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonAbilitiesAdapter.PokemonDetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_details_layout, parent, false)
        return PokemonDetailsViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return pokemonAbilitiesList.size
    }

    override fun onBindViewHolder(
        holder: PokemonAbilitiesAdapter.PokemonDetailsViewHolder,
        position: Int
    ) {
        holder.bind(pokemonAbilitiesList, position)
    }
}
