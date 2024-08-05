package com.firebaseauthentication.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import  androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.text.font.FontWeight
import com.firebaseauthentication.R


@Composable
fun DividerComponents(modifier: Modifier?) {
    modifier?.fillMaxWidth()?.let {
        Row(
        modifier = it,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.Gray,
            thickness = 1.dp,
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = stringResource(R.string.or), fontSize = 18.sp,
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.Gray,
            thickness = 1.dp,
        )
    }
    }
}

@Composable
fun ClickableTextComponent(modifier: Modifier, onTextClicked: (String) -> Unit) {
    val initialText = stringResource(R.string.already_have_an_account)
    val loginText = stringResource(R.string.login)
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.W500
        )) {
            pushStringAnnotation(tag = "LoginText", annotation = stringResource(R.string.login))
            append(loginText)
        }
    }
    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also { span ->
                if (span.item == loginText) {

                    onTextClicked(span.item)
                }
            }
    }, modifier = modifier)

}