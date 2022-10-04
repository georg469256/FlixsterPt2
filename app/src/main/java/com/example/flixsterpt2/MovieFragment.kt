package com.example.flixsterpt2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flixsterpt2.BuildConfig.API_KEY
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray

class MovieFragment: Fragment(), MovieInteractionListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_rv, container, false)
        val progressBar = view.findViewById<ProgressBar>(R.id.movieProgressBar)
        val recyclerView = view.findViewById<RecyclerView>(R.id.movieRV)
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ProgressBar, recyclerView: RecyclerView) {
        var genreMap = HashMap<Int, String>()
        val genreClient = AsyncHttpClient()
        genreClient.get("https://api.themoviedb.org/3/genre/movie/list?api_key=${API_KEY}", object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e("GenreAPIError", "Failed to call genre API: $response")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.i("GenreAPISuccess", "Genre API called successfully")
                val resultsJson = json?.jsonObject?.get("genres")
                //[{"id":28,"name":"Action"},{"id":12,"name":"Adventure"},{"id":16,"name":"Animation"},{"id":35,"name":"Comedy"},{"id":80,"name":"Crime"},{"id":99,"name":"Documentary"},{"id":18,"name":"Drama"},{"id":10751,"name":"Family"},{"id":14,"name":"Fantasy"},{"id":36,"name":"History"},{"id":27,"name":"Horror"},{"id":10402,"name":"Music"},{"id":9648,"name":"Mystery"},{"id":10749,"name":"Romance"},{"id":878,"name":"Science Fiction"},{"id":10770,"name":"TV Movie"},{"id":53,"name":"Thriller"},{"id":10752,"name":"War"},{"id":37,"name":"Western"}]
                var jsonArray = JSONArray(resultsJson.toString())
                for (i in 0 until jsonArray.length()) {
                    val genre = jsonArray.getJSONObject(i)
                    genreMap.put(genre.getInt("id"), genre.getString("name"))
                }
            }

        })

        val movieClient = AsyncHttpClient()

        movieClient.get("https://api.themoviedb.org/3/movie/top_rated?api_key=${API_KEY}", object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e("MovieAPIError", "Failed to call top rated API: $response")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.i("MovieAPISuccess", "Movie API called successfully")
                progressBar.visibility = View.GONE
                val resultsJSON : String = json?.jsonObject?.get("results").toString()
                val gson = Gson()
                val arrayMovieType = object : TypeToken<List<Movie>>() {}.type
                val models : List<Movie> = gson.fromJson(resultsJSON, arrayMovieType)
                for(movie in models) {
                    var genreArray = movie.genre_ids?.let { Array<String>(it.size){""} }
                    if (genreArray != null) {
                        for(i in genreArray.indices) {
                            movie.genre_ids?.let { genreMap.get(it.get(i))?.let { genreArray.set(i, it) } }
                        }
                        movie.genres = genreArray
                    }
                }
                recyclerView.adapter = MovieRVAdapter(models, this@MovieFragment)
            }

        })

    }

    override fun onItemClick(item: Movie) {
        Toast.makeText(context,"Test: title is ${item.title}", Toast.LENGTH_LONG).show()
    }
}