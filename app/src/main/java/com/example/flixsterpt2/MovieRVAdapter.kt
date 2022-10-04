package com.example.flixsterpt2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*

const val REVIEW_EXTRA = "REVIEW_EXTRA"

class MovieRVAdapter(private val movies: List<Movie>, private val mListener: MovieInteractionListener?):
    RecyclerView.Adapter<MovieRVAdapter.MovieViewHolder>() {
    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mMovie: Movie? = null
        val mMovieTitle: TextView = mView.findViewById(R.id.titleText)
        val mMovieGenre: TextView = mView.findViewById(R.id.genreText)
        val mMovieVoteAverage: TextView = mView.findViewById(R.id.voteAverageText)
        val mMovieDescription: TextView = mView.findViewById(R.id.descriptionText)
        val mMoviePoster: ImageView = mView.findViewById(R.id.posterImage)
        val mReviewButton: Button = mView.findViewById(R.id.reviewButton)


        override fun toString(): String {
            return "$mMovieTitle\n$mMovieGenre\n$mMovieVoteAverage\n$mMovieDescription\n"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.mMovie = movie
        holder.mMovieTitle.text = movie.title
        holder.mMovieGenre.text = Arrays.toString(movie.genres).replace("[\\[\\]]".toRegex(),"")
        holder.mMovieVoteAverage.text = movie.vote_average.toString() + "/10"
        holder.mMovieDescription.text = movie.overview
        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
            .centerInside()
            .into(holder.mMoviePoster)

        holder.mView.setOnClickListener{
            holder.mMovie?.let {
                movie -> mListener?.onItemClick(movie)
            }
        }

        holder.mReviewButton.setOnClickListener {
            val context = holder.mView.context
            val intent = Intent(context, ReviewActivity::class.java)
            intent.putExtra(REVIEW_EXTRA, movie)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}