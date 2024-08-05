package com.firebaseauthentication.ui.screen.register

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.firebaseauthentication.ComposableLifecycle
import com.firebaseauthentication.MainActivityViewModel
import com.firebaseauthentication.R
import com.firebaseauthentication.navigation.AppRouter
import com.firebaseauthentication.navigation.Screen
import com.firebaseauthentication.utils.ClickableTextComponent
import com.firebaseauthentication.utils.DividerComponents
import com.firebaseauthentication.utils.LoginField
import com.firebaseauthentication.utils.PasswordField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegisterScreen() {

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val mainActivityViewModel: MainActivityViewModel = viewModel()
    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                Log.d("TAG", "MainScreen: ON CREATE")
            }

            Lifecycle.Event.ON_START -> {
                Log.d("TAG", "MainScreen: ON START")

                /// Check if current user exists
                val currentUser = MainActivityViewModel.auth.currentUser
                if (currentUser != null) {
                    AppRouter.navigateTo(Screen.HomeScreen)
                }
            }

            Lifecycle.Event.ON_RESUME -> {
                Log.d("TAG", "MainScreen: ON RESUME")
            }

            Lifecycle.Event.ON_PAUSE -> {
                Log.d("TAG", "MainScreen: ON PAUSE")
            }

            Lifecycle.Event.ON_STOP -> {
                Log.d("TAG", "MainScreen: ON STOP")
            }

            Lifecycle.Event.ON_DESTROY -> {
                Log.d("TAG", "MainScreen: ON DESTROY")
            }

            else -> {
                Log.d("TAG", "MainScreen: Out of life cycle")
            }
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp),
    ) {
        ConstraintLayout {
            val (image, infoText, middleSection, registerBtn, orText, loginText) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.authentication_bg),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .aspectRatio(1.4f)
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.W500,
                        )
                    ) {
                        append(stringResource(id = R.string.register))
                    }
                    append('\n')
                    withStyle(
                        style = SpanStyle(
                            color = Color.Gray,
                            fontSize = 18.sp
                        )
                    ) {
                        append(stringResource(R.string.explore_with_compose))
                    }
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .constrainAs(infoText) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(middleSection.top)
                        width = Dimension.matchParent
                    }
            )
            Column(modifier = Modifier.constrainAs(middleSection) {
                top.linkTo(infoText.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(registerBtn.top)
                width = Dimension.matchParent
            }) {
                LoginField(
                    mainActivityViewModel,
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                )
                PasswordField(
                    mainActivityViewModel = mainActivityViewModel,
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                )
            }

            Button(
                onClick = {
                    focusManager.clearFocus()
                    mainActivityViewModel.validateForm {
                        lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                            val result = mainActivityViewModel.signUpFirebaseUser(context)
                            if (result != null) {
                                withContext(Dispatchers.Main) {
                                    AppRouter.navigateTo(Screen.LoginScreen)
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(registerBtn) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(orText.top, margin = 20.dp)
                        top.linkTo(middleSection.bottom, margin = 20.dp)
                        width = Dimension.matchParent
                    },
            ) {
                Text(text = stringResource(R.string.register))
            }

            DividerComponents(modifier = Modifier.constrainAs(orText) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(registerBtn.bottom)
                bottom.linkTo(loginText.top, margin = 20.dp)
            })

            ClickableTextComponent(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .constrainAs(loginText) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(orText.bottom)
                        bottom.linkTo(parent.bottom)
                    }) {
                Log.d("Clickable Text", "{${it}}")

                AppRouter.navigateTo(Screen.LoginScreen)
            }

        }
    }

}

@Preview
@Composable
fun RegisterPreview() {
    return RegisterScreen()
}
