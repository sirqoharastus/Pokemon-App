package com.decagon.android.sq007

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.android.sq007.Adapters.PokemonAdapterClass
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), Clicklistener {
    val baseUrl = "https://pokeapi.co/api/v2/pokemon/"
    lateinit var secondImplementation: FloatingActionButton
    lateinit var recyclerView: RecyclerView
    lateinit var interfaceInstance: PokemonApiInterface
    // lateinit var pokemonList: List<>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        secondImplementation = findViewById(R.id.fob)
        secondImplementation.setOnClickListener {
            val intent = Intent(this, SecondImplementationActivity::class.java)
            startActivity(intent)
        }
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
        Log.d("build", "check")
//            .create(PokemonApiInterface::class.java)

        interfaceInstance = retrofitBuilder.create()
//        val retrofitData = retrofitBuilder.getPokemonData()
//
//        retrofitData.enqueue(object : Callback<List<Pokemon>?>){
//
//        }
        val callResult: Call<PokemonData> = interfaceInstance.getPokemonData()

        callResult.enqueue(object : Callback<PokemonData> {
            override fun onFailure(call: Call<PokemonData>, t: Throwable) {
                Log.d("output4", "$t")
            }
            override fun onResponse(call: Call<PokemonData>, response: Response<PokemonData>) {
                if (response.isSuccessful) {
                    recyclerView.adapter = PokemonAdapterClass(response.body()!!.results, this@MainActivity, this@MainActivity)

                    Log.d("output1", response.message())
                    Log.d("output2", "$response")
                } else {
                    Log.d("output3", "${response.code()}")
                    return
                }
            }
        })
    }

    override fun onItemClicked(position: Int) {
        val callResultDetails: Call<Pokemon> = interfaceInstance.getPokemon("$position")
        callResultDetails.enqueue(object : Callback<Pokemon> {
            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.d("failure_response", "$t")
            }

            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    PokemonHolder.abilities = response.body()!!.abilities
                    PokemonHolder.moves = response.body()!!.moves
                    PokemonHolder.stats = response.body()!!.stats
                    PokemonHolder.position = position
                    PokemonHolder.name = response.body()!!.name

                    this@MainActivity.startActivity(Intent(this@MainActivity, PokemonDetailsActivity::class.java))
                } else {
                    Log.d("Failure", "${response.code()}")
                }
            }
        })
    }
}
