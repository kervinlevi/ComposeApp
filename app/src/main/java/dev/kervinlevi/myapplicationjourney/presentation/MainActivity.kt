package dev.kervinlevi.myapplicationjourney.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFormScreen
import dev.kervinlevi.myapplicationjourney.presentation.ui.theme.MyApplicationJourneyTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

            }
        }

        setContent {
            MyApplicationJourneyTheme {
                UpdateFormScreen()
            }
        }
    }
}
