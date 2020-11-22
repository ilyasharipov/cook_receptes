package com.testproject.recipes.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testproject.recipes.api.Network
import com.testproject.recipes.data.RecipeData
import com.testproject.recipes.data.RecipeList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    private var network: Network = Network()

    val error: LiveData<Throwable>
        get() = errorEvent

    val getAllRecipesComplete: LiveData<List<RecipeData>>
        get() = getAllRecipesEvent

    private var getAllRecipesEvent: MutableLiveData<List<RecipeData>> = MutableLiveData()
    private var errorEvent: MutableLiveData<Throwable> = MutableLiveData()

    fun getAllRecipes() {
        network.getAllRecipes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : DisposableSingleObserver<RecipeList>() {
                    override fun onSuccess(list: RecipeList) {
                        getAllRecipesEvent.value = list.recipes
                    }

                    override fun onError(e: Throwable) {
                        Log.d("ERRORS", "onError: ${e.message}")
                        errorEvent.value = e
                    }
                }
            )
    }

    fun clearEvents() {
        errorEvent =  MutableLiveData()
        getAllRecipesEvent =  MutableLiveData()
    }
}