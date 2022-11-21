package com.example.empowermentlabs.data

import com.example.empowermentlabs.data.models.RecipeItem as DomainItemRecipe
import com.example.empowermentlabs.data.db.models.RecipeItem as DbItemRecipe
import com.example.empowermentlabs.data.remote.models.RecipeItem as ServerItemRecipe
import com.example.empowermentlabs.data.models.Recipe as DomainRecipe
import com.example.empowermentlabs.data.db.models.Recipe as DbRecipe
import com.example.empowermentlabs.data.remote.models.Recipe as ServerRecipe


fun DomainItemRecipe.toDbItemRecipe(): DbItemRecipe = DbItemRecipe(image, id, title, imageType, favorite)

fun DbItemRecipe.toDomainItemRecipe(): DomainItemRecipe =
    DomainItemRecipe(image, id, title, imageType, favorite)

fun ServerItemRecipe.toDomainItemRecipe(): DomainItemRecipe =
    DomainItemRecipe(image, id, title, imageType, favorite)


fun DomainRecipe.toDbRecipe(): DbRecipe = DbRecipe(
    instructions,
    sustainable,
    glutenFree,
    veryPopular,
    title,
    aggregateLikes,
    readyInMinutes,
    sourceUrl,
    creditsText,
    dairyFree,
    servings,
    vegetarian,
    whole30,
    id,
    imageType,
    summary,
    image,
    veryHealthy,
    vegan,
    cheap,
    dishTypes,
    gaps,
    lowFodmap,
    license,
    weightWatcherSmartPoints,
    sourceName,
    spoonacularSourceUrl,
    ketogenic
)

fun DbRecipe.toDomainRecipe(): DomainRecipe = DomainRecipe(
    instructions,
    sustainable,
    glutenFree,
    veryPopular,
    title,
    aggregateLikes,
    readyInMinutes,
    sourceUrl,
    creditsText,
    dairyFree,
    servings,
    vegetarian,
    whole30,
    id,
    imageType,
    summary,
    image,
    veryHealthy,
    vegan,
    cheap,
    dishTypes,
    gaps,
    lowFodmap,
    license,
    weightWatcherSmartPoints,
    sourceName,
    spoonacularSourceUrl,
    ketogenic
)

fun ServerRecipe.toDomainRecipe(): DomainRecipe = DomainRecipe(
    instructions,
    sustainable,
    glutenFree,
    veryPopular,
    title,
    aggregateLikes,
    readyInMinutes,
    sourceUrl,
    creditsText,
    dairyFree,
    servings,
    vegetarian,
    whole30,
    id,
    imageType,
    summary,
    image,
    veryHealthy,
    vegan,
    cheap,
    dishTypes,
    gaps,
    lowFodmap,
    license,
    weightWatcherSmartPoints,
    sourceName,
    spoonacularSourceUrl,
    ketogenic
)