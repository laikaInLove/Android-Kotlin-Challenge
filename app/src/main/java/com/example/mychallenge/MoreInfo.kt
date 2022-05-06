package com.example.mychallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.example.mychallenge.models.*
import com.example.mychallenge.services.MovieApiInterface
import com.example.mychallenge.services.MovieApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreInfo : AppCompatActivity() {

    var movieId = ""
    var rating : RatingMovie = RatingMovie()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_info)

        val bundle = intent.extras
        movieId = bundle?.getString("More").toString()
        // Toast.makeText(this,movieId,Toast.LENGTH_SHORT).show()

        getMovieData { movie: MovieInfo ->
            val original_title = findViewById<TextView>(R.id.filmName)
            val release_date = findViewById<TextView>(R.id.ReleaseDate)
            val runtime = findViewById<TextView>(R.id.Duration)
            val genres = findViewById<TextView>(R.id.Genre)
            val originalLanguage = findViewById<TextView>(R.id.OriginalLanguage)
            val overview = findViewById<TextView>(R.id.Resume)
            val popularityMovie = findViewById<TextView>(R.id.Popularity)
            val info_image = findViewById<ImageView>(R.id.imageInfo)

            val IMAGE_BASE = "https://image.tmdb.org/t/p/w500"
            Glide.with(this).load(IMAGE_BASE + movie.poster_path).into(info_image)

            original_title.text = movie.title
            release_date.text = movie.releaseDate
            runtime.text = movie.runtime
            originalLanguage.text = movie.originalLanguage

            var genresLoop = ""
            for (i in movie.genres.indices){
                genresLoop += movie.genres.get(i).name + " "
            }
            genres.text = genresLoop
            overview.text = movie.overview?.substring(0, 120) + "..."
            popularityMovie.text = movie.popularityMovie.toString()
        }

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val buttonRate = findViewById<Button>(R.id.buttonRate)

        buttonRate.setOnClickListener(View.OnClickListener{

            rating.value = ratingBar.rating * 2

            if(rating.value == 0.0F){
                rating.value = 0.5F
            }

            getSession { response:SessionResponse ->
                ratingMovie(response.sessionId){
                        response : ResponseRating ->
                        Toast.makeText(this, "Successfully rating.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun getMovieData(callback: (MovieInfo) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)

        apiService.getMovieInfo(movieId).enqueue(object : Callback<MovieInfo> {

            override fun onFailure(call: Call<MovieInfo>, t: Throwable) {
            }

            override fun onResponse(call: Call<MovieInfo>, response: Response<MovieInfo>) {
                return callback(response.body()!!)
            }

        })
    }
    private fun ratingMovie(session: String,callback: (ResponseRating) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)

        apiService.rateMovie(movieId, session, rating).enqueue(object : Callback<ResponseRating> {

            override fun onFailure(call: Call<ResponseRating>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseRating>, response: Response<ResponseRating>) {

                Log.i("MovieInfo", "response $response ")
                return callback(response.body()!!)
            }
        })
    }
    private fun getSession(callback: (SessionResponse) -> Unit){

        Log.i("MovieInfo", "response llamado")

        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)

        apiService.getSession().enqueue(object : Callback<SessionResponse> {

            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                Log.i("MovieInfo", "response error")
                Log.i("MovieInfo", "response error llamado $t")
            }

            override fun onResponse(call: Call<SessionResponse>, response: Response<SessionResponse>) {

                return callback(response.body()!!)
            }

        })
    }

}
