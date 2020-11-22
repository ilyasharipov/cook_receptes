package com.testproject.recipes.data

data class RecipeData (
    var uuid: String,
    var name: String,
    var images: List<String>,
    var lastUpdated: Int,
    var description: String,
    var instructions: String,
    var difficulty: Int
)