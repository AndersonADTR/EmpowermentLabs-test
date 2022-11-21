package com.example.empowermentlabs.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empowermentlabs.data.models.Recipe
import com.example.empowermentlabs.data.models.RecipeItem
import com.example.empowermentlabs.data.remote.network.Resource
import com.example.empowermentlabs.data.repositories.recipe.RecipeRepository
import com.example.empowermentlabs.utils.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _state = MutableLiveData<Resource<Any>>()
    val state: LiveData<Resource<Any>> get() = _state

    private fun buildRecipeQuery(apiKey: String): Map<String, String> = mapOf(API_KEY to apiKey)

    fun getRemoteRecipe(apiKey: String, recipeId: Int) {
        _state.value = Resource.loading(null)
        viewModelScope.launch {
            _state.value = repository.requestRecipe(recipeId, buildRecipeQuery(apiKey))
        }
    }

    fun getLocalRecipe(recipeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(repository.requestDbRecipe(recipeId))
        }
    }

    fun getLocalRecipeItem(recipeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(repository.requestDbRecipeItem(recipeId))
        }
    }

    fun switchFavorite(recipeItem: RecipeItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.switchFavorite(recipeItem)
        }
    }

    fun saveData(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveData(recipe)
        }
    }
}