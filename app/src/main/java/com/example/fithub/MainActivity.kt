package com.example.fithub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.fithub.appRoutes.AppNavHost
import com.example.fithub.ui.theme.FitHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitHubRoot {
                AppNavHost()
            }
        }
    }
}

@Composable
fun FitHubRoot(
    content: @Composable () -> Unit
){
    FitHubTheme {
        Surface{
            content()
        }
    }
}