/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.recipes.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.recipes.RecipesApplication
import com.example.recipes.data.RecipesRepository
import com.example.recipes.model.Categories
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface RecipesUiState {
    data class Success(val categories: Categories) : RecipesUiState
    object Error : RecipesUiState
    object Loading : RecipesUiState
}


class RecipesViewModel(private val recipesRepository: RecipesRepository) : ViewModel() {

    var recipesUiState: RecipesUiState by mutableStateOf(RecipesUiState.Loading)
        private set

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            recipesUiState = RecipesUiState.Loading
            recipesUiState = try {
                RecipesUiState.Success(recipesRepository.getCategories())
            } catch (e: IOException) {
                RecipesUiState.Error
            } catch (e: HttpException) {
                RecipesUiState.Error
            }
            Log.d("Network", "Response: ${recipesUiState}")
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as RecipesApplication)
                val recipesRepository = application.container.recipesRepository
                RecipesViewModel(recipesRepository = recipesRepository)
            }
        }
    }
}
