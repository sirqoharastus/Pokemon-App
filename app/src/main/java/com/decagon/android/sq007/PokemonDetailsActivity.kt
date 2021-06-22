package com.decagon.android.sq007

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.decagon.android.sq007.Adapters.PokemonAbilitiesAdapter
import com.decagon.android.sq007.Adapters.PokemonMoveAdapter
import com.decagon.android.sq007.Adapters.StatsAdapter

class PokemonDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)
        // declaring recyclerviews
        val abilityRecyclerView: RecyclerView = findViewById(R.id.pokemon_abilities_recyclerview)
        val moveRecyclerView: RecyclerView = findViewById(R.id.moves_recyclerview)
        val statRecyclerView: RecyclerView = findViewById(R.id.stats_recyclerview)
        // declaring pokemon Imageview and using Glide to load the image gotten from the response
        val pokemonImage: ImageView = findViewById(R.id.PokemonDetailsImage)
        val pokemonName:TextView = findViewById(R.id.pokemon_details_name_textView)
        Glide.with(this)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${PokemonHolder.position + 1}.png")
            .into(pokemonImage)
        pokemonName.text = PokemonHolder.name
        // setting layout manager for the recyclerviews
        abilityRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        moveRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        statRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        // setting recyclerview adapters
        abilityRecyclerView.adapter = PokemonHolder.abilities?.let { PokemonAbilitiesAdapter(it) }
        moveRecyclerView.adapter = PokemonHolder.moves?.let { PokemonMoveAdapter(it) }
        statRecyclerView.adapter = PokemonHolder.stats?.let { StatsAdapter(it) }
    }
}
