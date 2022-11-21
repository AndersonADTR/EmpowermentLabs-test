package com.example.empowermentlabs.data.remote.models

import com.google.gson.annotations.SerializedName

data class RecipesResponse(

	@field:SerializedName("number")
	val number: Int? = null,

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("results")
	val results: List<RecipeItem?>? = null
)


data class RecipeItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("id")
	val id: Int = 0,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("imageType")
	val imageType: String? = null,

	val favorite: Boolean = false
)