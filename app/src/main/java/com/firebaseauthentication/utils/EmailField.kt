package com.firebaseauthentication.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.firebaseauthentication.MainActivityViewModel
import com.firebaseauthentication.R

@Composable
fun LoginField(
    mainActivityViewModel: MainActivityViewModel,
    modifier: Modifier = Modifier,
    errorModifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val emailError = mainActivityViewModel.emailError?.asString()
    val isError = !emailError.isNullOrEmpty()
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Email,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    OutlinedTextField(
        value = mainActivityViewModel.email,
        leadingIcon = leadingIcon,
        isError = isError,

        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email,
        ),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        modifier = modifier.then(Modifier.fillMaxWidth()),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = Color.Gray,
            unfocusedBorderColor = Color.Gray,
            focusedTextColor = Color.Blue,
            focusedBorderColor = Color.Blue,
            errorBorderColor = Color.Red,
        ),
        shape = RoundedCornerShape(10.dp),
        onValueChange = mainActivityViewModel::updateEmail,
        label = { Text(stringResource(R.string.enter_email)) }
    )
    if (isError) {
        Text(
            text = emailError!!,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Red,
            modifier = errorModifier.then(Modifier.fillMaxWidth()),
            textAlign = TextAlign.Start
        )
    }
}