package com.hoa.logindemo.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.hoa.logindemo.R
import com.hoa.logindemo.ui.extension.EditableTextState

@Composable
fun ErrorMessageText(
    modifier: Modifier,
    description: String,
    errorState: EditableTextState
) {
    AnimatedVisibility(
        visible = errorState.isNotEmpty(),
        enter = fadeIn(initialAlpha = 0.4F),
        exit = fadeOut(animationSpec = tween(250))
    ) {
        Text(
            text = errorState.value,
            color = colorResource(R.color.error_color),
            modifier = modifier.semantics { contentDescription = description }
        )
    }
}