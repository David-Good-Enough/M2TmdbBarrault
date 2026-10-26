package com.example.m2tmdbbarrault.data.remote

import com.google.gson.annotations.SerializedName


data class KnownFor (

  @SerializedName("adult"             ) var adult            : Boolean,
  @SerializedName("backdrop_path"     ) var backdropPath     : String,
  @SerializedName("id"                ) var id               : Int,
  @SerializedName("name"              ) var name             : String,
  @SerializedName("original_name"     ) var originalName     : String,
  @SerializedName("overview"          ) var overview         : String,
  @SerializedName("poster_path"       ) var posterPath       : String,
  @SerializedName("media_type"        ) var mediaType        : String,
  @SerializedName("original_language" ) var originalLanguage : String,
  @SerializedName("genre_ids"         ) var genreIds         : ArrayList<Int>    = arrayListOf(),
  @SerializedName("popularity"        ) var popularity       : Double,
  @SerializedName("first_air_date"    ) var firstAirDate     : String,
  @SerializedName("softcore"          ) var softcore         : Boolean,
  @SerializedName("vote_average"      ) var voteAverage      : Double,
  @SerializedName("vote_count"        ) var voteCount        : Int,
  @SerializedName("origin_country"    ) var originCountry    : ArrayList<String> = arrayListOf()

)