package com.example.mychallenge

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.AbsListView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mychallenge.models.Movie
import com.example.mychallenge.models.MovieResponse
import com.example.mychallenge.services.MovieApiInterface
import com.example.mychallenge.services.MovieApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var page = 1;
    private var moviesList : List<Movie> = listOf()
    private lateinit var rv_movies_list : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_movies_list = findViewById<RecyclerView>(R.id.rv_movies_list)

        rv_movies_list.layoutManager = LinearLayoutManager(this)
        rv_movies_list.setHasFixedSize(true)

        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchView : SearchView = findViewById(R.id.search)

        searchView.setSearchableInfo(
            sm.getSearchableInfo(
                ComponentName(this, this::class.java)
            )
        )

        searchView.setOnCloseListener {
            Log.i("View", "response: CLOSE!")
            false
        }

        getMovieData { movies : List<Movie> ->
            rv_movies_list.adapter = MovieAdapter(movies)
            moviesList = movies;
        }

        handleSearchIntent(intent)

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleSearchIntent(intent)
    }

    private fun handleSearchIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            var text = intent.getStringExtra(SearchManager.QUERY);
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                searchExpenses(query)
            }
        }

    }

    fun search(query: String): List<Movie> {
        if (query.isBlank())
            return emptyList()

        return moviesList.filter { movie ->
            val regex = query.toRegex(RegexOption.IGNORE_CASE)
            regex.containsMatchIn(movie.title.toString())
        }
    }

    private fun searchExpenses(query: String) {
        val searchResults = search(query)
        rv_movies_list.adapter = MovieAdapter(searchResults)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu, menu)

        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(
            sm.getSearchableInfo(
                ComponentName(this, ResultsActivity::class.java)
            )
        )
        return true
    }

    private fun getMovieData(callback: (List<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieList(page).enqueue(object : Callback<MovieResponse>{

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return callback(response.body()!!.movies)
            }

        })
    }
}
