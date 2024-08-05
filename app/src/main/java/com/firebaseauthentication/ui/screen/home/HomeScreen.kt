package com.firebaseauthentication.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.firebaseauthentication.MainActivityViewModel
import com.firebaseauthentication.R
import com.firebaseauthentication.navigation.AppRouter
import com.firebaseauthentication.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val mainActivityViewModel: MainActivityViewModel = viewModel()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    Column {
        TopAppBar(
            title = { Text(text = "Home") },
            actions = {
                IconButton(
                    onClick = {
                        lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                            mainActivityViewModel.logoutFirebaseUser(context)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.successfull_logged_out),
                                    Toast.LENGTH_SHORT,
                                ).show()
                                AppRouter.navigateTo(Screen.LoginScreen)
                            }
                        }
                    },
                ) {
                    Icon(Icons.Outlined.Logout, contentDescription = "")
                }
            },
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
        ) {
            Text(
                text = "Welcome",
                textAlign = TextAlign.Center,
            )
        }
    }
}
