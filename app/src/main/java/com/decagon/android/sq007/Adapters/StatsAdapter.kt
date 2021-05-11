package com.decagon.android.sq007.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.PokemonDetails.Stat
import com.decagon.android.sq007.R

class StatsAdapter(var statList: List<Stat>) : RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {

    inner class StatsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val statsTextView: TextView = view.findViewById(R.id.details_textview)
        fun bind(statList: List<Stat>, position: Int) {
            statsTextView.text = statList[position].stat.name
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatsAdapter.StatsViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_details_layout, parent, false)
        return StatsViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return statList.size
    }

    override fun onBindViewHolder(holder: StatsAdapter.StatsViewHolder, position: Int) {
        holder.bind(statList, position)
    }
}
