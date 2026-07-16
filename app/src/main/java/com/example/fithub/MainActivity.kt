package com.example.fithub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.example.fithub.appRoutes.AppNavHost
import com.example.fithub.sessionManager.SessionManager
import com.example.fithub.ui.theme.FitHubTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme by sessionManager.isDarkTheme.collectAsState(
                initial = true
            )

            FitHubRoot(
                isDarkTheme = isDarkTheme
            ) {
                AppNavHost(
                    isDarkTheme = isDarkTheme,
                    onThemeChange = { newThemeValue ->
                        lifecycleScope.launch {
                            sessionManager.saveDarkTheme(newThemeValue)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FitHubRoot(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
){
    FitHubTheme(
        darkTheme = isDarkTheme
    ) {
        Surface{
            content()
        }
    }
}