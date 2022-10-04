package com.example.flixsterpt2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flixsterpt2.BuildConfig.API_KEY
import com.example.flixsterpt2.databinding.ActivityReviewBinding
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

class ReviewActivity : AppCompatActivity() {
    private lateinit var articlesRecyclerView: RecyclerView
    private lateinit var binding: ActivityReviewBinding
    private val reviews = mutableListOf<Review>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReviewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        articlesRecyclerView = findViewById(R.id.reviewRV)
        val reviewAdapter = ReviewRVAdapter(reviews)
        articlesRecyclerView.adapter = reviewAdapter

        articlesRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            articlesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        val client = AsyncHttpClient()
        val movie = intent.getParcelableExtra(REVIEW_EXTRA) as? Movie
        if (movie != null) {
            client.get("https://api.themoviedb.org/3/movie/${movie.id}/reviews?api_key=${API_KEY}", object : JsonHttpResponseHandler() {
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    response: String?,
                    throwable: Throwable?
                ) {
                    Log.e("ReviewAPIFail", "Failed to fetch articles: $statusCode")
                }

                override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                    Log.i("ReviewAPISuccess", "Successfully fetched articles: $json")
                    binding.reviewProgressBar.visibility = View.GONE
                    try {
                        val parsedJson = createJson().decodeFromString(
                            BaseResponse.serializer(),
                            json.jsonObject.toString()
                        )

                        parsedJson.reviewList?.let { list ->
                            reviews.addAll(list)
                        }

                        reviewAdapter.notifyDataSetChanged()

                    } catch (e: JSONException) {
                        Log.e("JSON Error", "Exception: $e")
                    }
                }

            })
        }

    }
}