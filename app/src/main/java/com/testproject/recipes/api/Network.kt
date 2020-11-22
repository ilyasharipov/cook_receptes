package com.testproject.recipes.api

import com.testproject.recipes.data.RecipeList
import com.testproject.recipes.data.RecipeRaw
import io.reactivex.Single

class Network {
    private val baseURL = "https://test.kode-t.ru/"
    private var recipesApi = RecipesApi.Builder(baseURL).build()

    fun getRecipeByUUID(uuid: String): Single<RecipeRaw> = recipesApi.getRecipeByUUID(uuid)
    fun getAllRecipes(): Single<RecipeList> = recipesApi.getAllRecipes()
}