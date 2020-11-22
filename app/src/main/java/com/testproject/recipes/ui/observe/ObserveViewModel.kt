package com.testproject.recipes.ui.observe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testproject.recipes.api.Network
import com.testproject.recipes.data.RecipeData
import com.testproject.recipes.data.RecipeRaw
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ObserveViewModel : ViewModel() {
    private var network: Network = Network()

    val error: LiveData<Throwable>
        get() = errorEvent

    val getGetRecipeByUUIDComplete: LiveData<RecipeData>
        get() = getGetRecipeByUUIDEvent

    private var getGetRecipeByUUIDEvent: MutableLiveData<RecipeData> = MutableLiveData()
    private var errorEvent: MutableLiveData<Throwable> = MutableLiveData()

    fun getRecipeByUUID(uuid: String) {
        network.getRecipeByUUID(uuid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : DisposableSingleObserver<RecipeRaw>() {
                    override fun onSuccess(data: RecipeRaw) {
                        getGetRecipeByUUIDEvent.value = data.recipe
                    }

                    override fun onError(e: Throwable) {
                        errorEvent.value = e
                    }
                }
            )
    }

    fun clearEvents() {
        errorEvent =  MutableLiveData()
        getGetRecipeByUUIDEvent =  MutableLiveData()
    }
}