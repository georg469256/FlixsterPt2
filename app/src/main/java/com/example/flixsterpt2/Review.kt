package com.example.flixsterpt2

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


    @Keep
    @Serializable
    data class BaseResponse(
        @SerialName("results")
        val reviewList: List<Review>?
    ): java.io.Serializable

    @Keep
    @Serializable
    data class Review(
        @SerialName("author")
        var author: String? = null,

        var authorDetails: AuthorDetails? = null,

        @SerialName("content")
        var content: String? = null,

        @SerialName("created_at")
        var created_at: String? = null,

        @SerialName("id")
        var id: String? = null,

        @SerialName("updated_at")
        var updated_at: String? = null,

        @SerialName("url")
        var url: String? = null,
    ): java.io.Serializable

    @Keep
    @Serializable
    data class AuthorDetails(
        @SerialName("author_details")
        var response: Details?
    ): java.io.Serializable

    @Keep
    @Serializable
    data class Details(
        @SerialName("name")
        var name: String? = null,
        @SerialName("username")
        var username: String? = null,
        @SerialName("avatar_path")
        var avatar_path: String? = null,
        @SerialName("rating")
        var rating: Double? = null
    ): java.io.Serializable