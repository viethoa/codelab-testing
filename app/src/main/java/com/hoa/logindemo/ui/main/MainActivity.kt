package com.hoa.logindemo.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.hoa.logindemo.ui.theme.LoginDemoTheme
import java.util.concurrent.atomic.AtomicBoolean

class MainActivity : ComponentActivity() {
    private val isRecovering: AtomicBoolean = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isRecovering.set(true)
        isRecovering.compareAndSet(false, true)
        setContent {
            MainScreen()
        }
    }
}

@Composable
private fun MainScreen() {
    LoginDemoTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .semantics { contentDescription = "mainScreen" },
            color = MaterialTheme.colorScheme.background
        ) {
            Text("Main Screen")
        }
    }
}