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
import androidx.recyclerview.widget.RecyclerView
import kotlin.coroutines.cancellation.CancellationException

class MainActivity : AppCompatActivity() {
    private val LOGTAG= MainActivity::class.simpleName
    private var persons = arrayListOf<Person>()
    private lateinit var personAdapter: PersonAdapter
    private var totalPage = Int.MAX_VALUE
    private var curPage = 1
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
        personAdapter = PersonAdapter(persons)
        binding.popularPersonRv.adapter = personAdapter

        // Infinite scrolling
        binding.popularPersonRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy <= 0 || binding.loading || curPage > totalPage) return
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                if (lastVisible >= layoutManager.itemCount - 1) {
                    loadCurrentPage()
                }
            }
        })


        loadCurrentPage()
    }
        private fun loadCurrentPage() {


            val api = RetrofitClient.create(ITmdbAPI::class.java)
            lifecycleScope.launch {
                binding.loading = true
                try {


                    val response = api.getPopularPersons()
                    totalPage = response.totalPages
                    persons.addAll(response.results)
                    persons.sortByDescending { it.popularity }
                    personAdapter.notifyDataSetChanged()
                    curPage++
                    binding.totalResultsText =
                        getString(R.string.total_results_text, persons.size, response.totalResults)
                    Log.d(LOGTAG, "Popular persons: ${response.results.size}")
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    Log.e(LOGTAG, "Unable to load popular persons", e)
                } finally {
                    binding.loading = false
                }
            }
        }



}