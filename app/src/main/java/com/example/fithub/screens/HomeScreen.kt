package com.example.fithub.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fithub.viewModels.HomeViewModel

@Composable
fun HomeScreen (
    viewModel: HomeViewModel
){
    val uiState by viewModel.uiState.collectAsState()
    val message = uiState.message ?: "NULL"

    LaunchedEffect(Unit) {
        viewModel.testUi()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Text(text = message)
    }
}

