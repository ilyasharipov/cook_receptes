package com.testproject.recipes.api

import com.testproject.recipes.data.RecipeList
import com.testproject.recipes.data.RecipeRaw
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipesApi {
    @GET("recipes")
    fun getAllRecipes(): Single<RecipeList>

    @GET("recipes/{uuid}")
    fun getRecipeByUUID(@Path("uuid") uuid: String): Single<RecipeRaw>

    class Builder(private val baseUrl: String)
    {
        fun build(): RecipesApi
        {
            return (Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()).create()
        }
    }
}