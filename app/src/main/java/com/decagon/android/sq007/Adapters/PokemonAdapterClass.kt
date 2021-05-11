package com.decagon.android.sq007.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decagon.android.sq007.Clicklistener
import com.decagon.android.sq007.PokemonDataGotten
import com.decagon.android.sq007.R

class PokemonAdapterClass(
    val pokemonList: List<PokemonDataGotten>,
    var context: Context,
    var listener: Clicklistener
) : RecyclerView.Adapter<PokemonAdapterClass.PokemonViewHolder>() {

    inner class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.pokemon_card_imageview)
        val descriptionText = view.findViewById<TextView>(R.id.pokemon_name_textview)

        fun bind(item: List<PokemonDataGotten>, position: Int) {
            descriptionText.text = item[position].name
            Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${position + 1}.png")
                .into(image)
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonAdapterClass.PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_card_layout, parent, false)
        return PokemonViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: PokemonAdapterClass.PokemonViewHolder, position: Int) {
        holder.bind(pokemonList, position)

        holder.itemView.setOnClickListener {
            listener.onItemClicked(position)
        }
    }
}
