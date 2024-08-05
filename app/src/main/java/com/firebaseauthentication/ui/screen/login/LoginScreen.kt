package com.firebaseauthentication.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.firebaseauthentication.MainActivityViewModel
import com.firebaseauthentication.R
import com.firebaseauthentication.navigation.AppRouter
import com.firebaseauthentication.navigation.Screen
import com.firebaseauthentication.utils.LoginField
import com.firebaseauthentication.utils.PasswordField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen() {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp),
    ) {
        val focusManager = LocalFocusManager.current
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current

        val mainActivityViewModel: MainActivityViewModel = viewModel()
        ConstraintLayout {
            val (image, infoText, middleSection, loginBtn) = createRefs()

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
                        append(stringResource(id = R.string.login))
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
                    .padding(vertical = 6.dp)
                    .constrainAs(infoText) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(middleSection.top)
                        width = Dimension.matchParent
                    }

            )
            Column(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .constrainAs(middleSection) {
                        top.linkTo(infoText.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(loginBtn.top)
                        width = Dimension.matchParent
                    }
            ) {
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
                            val result = mainActivityViewModel.signInFirebaseUser(context)
                            if (result != null) {
                                withContext(Dispatchers.Main) {
                                    AppRouter.navigateTo(Screen.HomeScreen)
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .wrapContentWidth(
                        align = Alignment.End
                    )
                    .padding(vertical = 8.dp)
                    .constrainAs(loginBtn) {
                        top.linkTo(middleSection.bottom)
                        width = Dimension.matchParent
                    },
            ) {
                Text(text = stringResource(id = R.string.login))
            }
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    return LoginScreen()
}