package com.example.mychallenge

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mychallenge.models.Movie

class MovieAdapter(

    private val movies : List<Movie>

) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    class MovieViewHolder(view : View) : RecyclerView.ViewHolder(view){

        val movie_title = view.findViewById<TextView>(R.id.movie_title)
        val release_date = view.findViewById<TextView>(R.id.release_date)
        val movie_image = view.findViewById<ImageView>(R.id.movie_image)
        private val moreInfoButton: Button = view.findViewById<Button>(R.id.moreInfoButton)

        private val context: Context = view.context;

        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500"
        fun bindMovie(movie : Movie){

            movie_title.text = movie.title
            release_date.text = movie.release_date

            Glide.with(itemView).load(IMAGE_BASE + movie.poster_path).into(movie_image)

            moreInfoButton.setOnClickListener{

                val intent = Intent(context, MoreInfo::class.java)
                intent.putExtra("More", movie.id)
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movies.get(position))
    }

}