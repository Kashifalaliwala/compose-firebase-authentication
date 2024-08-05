package com.firebaseauthentication.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.firebaseauthentication.MainActivityViewModel
import com.firebaseauthentication.R

@Composable
fun PasswordField(mainActivityViewModel: MainActivityViewModel, modifier: Modifier = Modifier) {
    val focusManager = LocalFocusManager.current
    val passwordError = mainActivityViewModel.passwordError?.asString()
    val isError = !passwordError.isNullOrEmpty()
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Lock,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    OutlinedTextField(
        value = mainActivityViewModel.password,
        leadingIcon = leadingIcon,
        isError = isError,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password,
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
        onValueChange = mainActivityViewModel::updatePassword,
        label = { Text(stringResource(R.string.enter_password)) }
    )
    if (isError) {
        Text(
            text = passwordError!!,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 2.dp),
            textAlign = TextAlign.Start
        )
    }
}