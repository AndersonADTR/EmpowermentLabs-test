package com.example.empowermentlabs.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empowermentlabs.data.models.RecipeItem
import com.example.empowermentlabs.data.remote.network.Resource
import com.example.empowermentlabs.data.repositories.resipes.RecipesRepository
import com.example.empowermentlabs.utils.API_KEY
import com.example.empowermentlabs.utils.OFFSET
import com.example.empowermentlabs.utils.PAGE_SIZE
import com.example.empowermentlabs.utils.SEARCH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val repository: RecipesRepository
) : ViewModel() {

    private val _state = MutableLiveData<Resource<Any>>()
    val state: LiveData<Resource<Any>> get() = _state

    val pageSize: Int = 10
    var offset: Int = 0

    private fun buildRecipeQuery(
        apiKey: String,
        search: String,
        offset: String
    ): Map<String, String> =
        mapOf(
            API_KEY to apiKey,
            SEARCH to search,
            OFFSET to offset,
            PAGE_SIZE to pageSize.toString()
        )

    fun getRemoteRecipes(apiKey: String, search: String, offset: String) {
        _state.value = Resource.loading(null)
        viewModelScope.launch {
            _state.value = repository.requestRecipes(buildRecipeQuery(apiKey, search, offset))
        }
    }

    fun getLocalRecipes() {
        viewModelScope.launch(IO) {
            _state.postValue(repository.requestDbRecipes())
        }
    }

    fun findByTitle(search: String) {
        viewModelScope.launch(IO) {
            _state.postValue(repository.findByTitle(search))
        }
    }

    fun findByFavorites() {
        viewModelScope.launch(IO) {
            _state.postValue(repository.findByFavorites())
        }
    }

    fun switchFavorite(recipe: RecipeItem) {
        viewModelScope.launch(IO) {
            repository.switchFavorite(recipe)
        }
    }

    fun saveData(recipes: List<RecipeItem>) {
        viewModelScope.launch(IO) {
            repository.saveData(recipes)
        }
    }
}