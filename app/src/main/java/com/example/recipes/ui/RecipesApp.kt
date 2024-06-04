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

package com.example.recipes.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipes.R
import com.example.recipes.ui.screens.RecipesViewModel
import com.example.recipes.ui.screens.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Image(painter = painterResource(id = R.drawable.photo_1717507044866
                    ), contentDescription = "logo")
                },
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val recipesViewModel: RecipesViewModel =
                viewModel(factory = RecipesViewModel.Factory)
            HomeScreen(
                recipesUiState = recipesViewModel.recipesUiState,
                retryAction = recipesViewModel::getCategories,
                modifier = Modifier.fillMaxSize(),
                contentPadding = it
            )
        }
    }
}
