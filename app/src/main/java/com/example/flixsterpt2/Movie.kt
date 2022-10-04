package com.example.flixsterpt2

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

class Movie() : Parcelable {

    @SerializedName("adult")
    var adult: Boolean? = null

    @SerializedName("backdrop_path")
    var backdrop_path: String? = null

    @SerializedName("genre_ids")
    var genre_ids: Array<Int>? = null

    var genres: Array<String>? = null

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("original_language")
    var original_language: String? = null

    @SerializedName("original_title")
    var original_title: String? = null

    @SerializedName("overview")
    var overview: String? = null

    @SerializedName("popularity")
    var popularity: Double? = null

    @SerializedName("poster_path")
    var poster_path: String? = null

    @SerializedName("release_date")
    var release_date: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("video")
    var video: Boolean? = null

    @SerializedName("vote_average")
    var vote_average: Double? = null

    @SerializedName("vote_count")
    var vote_count: Int? = null

      constructor(parcel: Parcel) : this() {
          adult = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
          backdrop_path = parcel.readString()
          genres = parcel.createStringArray()
          id = parcel.readValue(Int::class.java.classLoader) as? Int
          original_language = parcel.readString()
          original_title = parcel.readString()
          overview = parcel.readString()
          popularity = parcel.readValue(Double::class.java.classLoader) as? Double
          poster_path = parcel.readString()
          release_date = parcel.readString()
          title = parcel.readString()
          video = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
          vote_average = parcel.readValue(Double::class.java.classLoader) as? Double
          vote_count = parcel.readValue(Int::class.java.classLoader) as? Int
      }

      override fun writeToParcel(parcel: Parcel, flags: Int) {
          parcel.writeValue(adult)
          parcel.writeString(backdrop_path)
          parcel.writeStringArray(genres)
          parcel.writeValue(id)
          parcel.writeString(original_language)
          parcel.writeString(original_title)
          parcel.writeString(overview)
          parcel.writeValue(popularity)
          parcel.writeString(poster_path)
          parcel.writeString(release_date)
          parcel.writeString(title)
          parcel.writeValue(video)
          parcel.writeValue(vote_average)
          parcel.writeValue(vote_count)
      }

      override fun describeContents(): Int {
          return 0
      }

      companion object CREATOR : Parcelable.Creator<Movie> {
          override fun createFromParcel(parcel: Parcel): Movie {
              return Movie(parcel)
          }

          override fun newArray(size: Int): Array<Movie?> {
              return arrayOfNulls(size)
          }
      }
  }