package com.example.flixsterpt2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewRVAdapter(private val reviews: List<Review>):
    RecyclerView.Adapter<ReviewRVAdapter.ReviewViewHolder>() {
    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class ReviewViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mReview: Review? = null
        var mReviewNameText: TextView = mView.findViewById(R.id.reviewNameText)
        var mReviewRatingText: TextView = mView.findViewById(R.id.reviewRatingText)
        var mReviewText: TextView = mView.findViewById(R.id.reviewText)
        var mReviewCreatedText: TextView = mView.findViewById(R.id.reviewCreatedText)
        var mReviewUpdatedText: TextView = mView.findViewById(R.id.reviewUpdatedText)


        override fun toString(): String {
            return "Written by: ${mReviewNameText.text}. Rating: ${mReviewRatingText.text}.\n"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.mReview = review
        holder.mReviewNameText.text = review.author
        holder.mReviewRatingText.text = "${review.authorDetails?.response?.username}/10"
        holder.mReviewText.text = review.content
        holder.mReviewCreatedText.text = "Review Created: ${review.created_at?.let { review.created_at!!.substring(0, it.indexOf("T")) }}"
        holder.mReviewUpdatedText.text = "Review Updated: ${review.updated_at?.let { review.updated_at!!.substring(0, it.indexOf("T")) }}"
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
}