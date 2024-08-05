package com.firebaseauthentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.firebaseauthentication.navigation.AppRouter
import com.firebaseauthentication.navigation.Screen
import com.firebaseauthentication.ui.screen.home.HomeScreen
import com.firebaseauthentication.ui.screen.login.LoginScreen
import com.firebaseauthentication.ui.screen.register.RegisterScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {

                Crossfade(
                    targetState = AppRouter.currentScreen,
                    label = AppRouter.currentScreen.value.toString()
                ) { currentState ->
                    when (currentState.value) {
                        is Screen.SignUpScreen -> {
                            RegisterScreen()
                        }

                        is Screen.LoginScreen -> {
                            LoginScreen()
                        }

                        is Screen.HomeScreen -> {
                            HomeScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ComposableLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit
) {

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            onEvent(source, event)
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
