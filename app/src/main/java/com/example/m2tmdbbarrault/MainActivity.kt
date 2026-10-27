package com.example.m2tmdbbarrault

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m2tmdbbarrault.data.remote.ITmdbAPI
import com.example.m2tmdbbarrault.data.remote.Person
import com.example.m2tmdbbarrault.data.remote.RetrofitClient
import com.example.m2tmdbbarrault.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val LOGTAG= MainActivity::class.simpleName
    private var persons = arrayListOf<Person>()
    private lateinit var personAdapter: PersonAdapter
    private var totalPage = Int.MAX_VALUE
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        binding.totalResultsText = getString(R.string.total_results_default_text)

        binding.popularPersonRv.setHasFixedSize(true)
        binding.popularPersonRv.layoutManager = LinearLayoutManager(this)
        binding.popularPersonRv.adapter = PersonAdapter(persons)

        val api = RetrofitClient.create(ITmdbAPI::class.java)
        lifecycleScope.launch {
            val response = api.getPopularPersons()
            totalPage = response.totalPages
            persons.addAll(response.results)
            personAdapter.notifyDataSetChanged()
            binding.totalResultsText = getString(R.string.total_results_text, persons.size, response.totalResults)
            Log.d(LOGTAG, "Popular persons: ${response.results.size}")
        }


    }
}