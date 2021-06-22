package com.decagon.android.sq007.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.PokemonDetails.Move
import com.decagon.android.sq007.R

class PokemonMoveAdapter(val pokemonMovesList: List<Move>) : RecyclerView.Adapter<PokemonMoveAdapter.PokemonMovesViewHolder>() {
    inner class PokemonMovesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonMovesTextView: TextView = view.findViewById(R.id.details_textview)
        fun bind(MoveList: List<Move>, position: Int) {
            pokemonMovesTextView.text = pokemonMovesList[position].move.name
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonMoveAdapter.PokemonMovesViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_details_layout, parent, false)
        return PokemonMovesViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return pokemonMovesList.size
    }

    override fun onBindViewHolder(
        holder: PokemonMoveAdapter.PokemonMovesViewHolder,
        position: Int
    ) {
        holder.bind(pokemonMovesList, position)
    }
}
