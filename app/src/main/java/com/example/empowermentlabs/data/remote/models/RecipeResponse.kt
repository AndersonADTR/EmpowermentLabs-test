package com.example.empowermentlabs.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Recipe(

	@Expose
	@field:SerializedName("instructions")
	val instructions: String? = null,

	@Expose
	@field:SerializedName("sustainable")
	val sustainable: Boolean? = null,

	@Expose
	@field:SerializedName("glutenFree")
	val glutenFree: Boolean? = null,

	@Expose
	@field:SerializedName("veryPopular")
	val veryPopular: Boolean? = null,

	@Expose
	@field:SerializedName("title")
	val title: String? = null,

	@Expose
	@field:SerializedName("aggregateLikes")
	val aggregateLikes: Int? = null,

	@Expose
	@field:SerializedName("readyInMinutes")
	val readyInMinutes: Int? = null,

	@Expose
	@field:SerializedName("sourceUrl")
	val sourceUrl: String? = null,

	@Expose
	@field:SerializedName("creditsText")
	val creditsText: String? = null,

	@Expose
	@field:SerializedName("dairyFree")
	val dairyFree: Boolean? = null,

	@Expose
	@field:SerializedName("servings")
	val servings: Int? = null,

	@Expose
	@field:SerializedName("vegetarian")
	val vegetarian: Boolean? = null,

	@Expose
	@field:SerializedName("whole30")
	val whole30: Boolean? = null,

	@Expose
	@field:SerializedName("id")
	val id: Int = 0,

	@Expose
	@field:SerializedName("imageType")
	val imageType: String? = null,

	@Expose
	@field:SerializedName("summary")
	val summary: String? = null,

	@Expose
	@field:SerializedName("image")
	val image: String? = null,

	@Expose
	@field:SerializedName("veryHealthy")
	val veryHealthy: Boolean? = null,

	@Expose
	@field:SerializedName("vegan")
	val vegan: Boolean? = null,

	@Expose
	@field:SerializedName("cheap")
	val cheap: Boolean? = null,

	@Expose
	@field:SerializedName("dishTypes")
	val dishTypes: List<String> = emptyList(),

	@Expose
	@field:SerializedName("gaps")
	val gaps: String? = null,

	@Expose
	@field:SerializedName("lowFodmap")
	val lowFodmap: Boolean? = null,

	@Expose
	@field:SerializedName("license")
	val license: String? = null,

	@Expose
	@field:SerializedName("weightWatcherSmartPoints")
	val weightWatcherSmartPoints: Int? = null,

	@Expose
	@field:SerializedName("sourceName")
	val sourceName: String? = null,

	@Expose
	@field:SerializedName("spoonacularSourceUrl")
	val spoonacularSourceUrl: String? = null,

	@Expose
	@field:SerializedName("ketogenic")
	val ketogenic: Boolean? = null
)
