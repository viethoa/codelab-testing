package com.hoa.logindemo.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoundedCornerButton(
    modifier: Modifier,
    @StringRes textRes: Int,
    enable: Boolean,
    description: String,
    onLoginClicked: () -> Unit
) {
    Button(
        enabled = enable,
        onClick = onLoginClicked,
        shape = RoundedCornerShape(6.dp),
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = description }
    ) {
        Text(
            fontSize = 18.sp,
            color = Color.White,
            text = stringResource(textRes),
            modifier = Modifier.padding(6.dp)
        )
    }
}