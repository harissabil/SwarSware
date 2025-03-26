package com.harissabil.swarsware

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.harissabil.swarsware.ui.navigation.NavGraph
import com.harissabil.swarsware.ui.theme.SwarSwareTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.splashCondition
            }
        }
        enableEdgeToEdge()
        setContent {
            SwarSwareTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavGraph(
                        startDestination = viewModel.startRoute
                    )
                }
            }
        }
    }
}