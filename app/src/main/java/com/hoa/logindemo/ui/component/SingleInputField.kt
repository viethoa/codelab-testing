package com.hoa.logindemo.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.hoa.logindemo.ui.theme.Grey
import com.hoa.logindemo.ui.theme.LoginDemoTheme

@Composable
fun SingleInputField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    onTextChanged: ((String) -> Unit)? = null
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            onTextChanged?.invoke(it)
        },
        label = {
            Text(color = Grey, text = label)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        )
    )
}

@Preview
@Composable
private fun SingleInputFieldPreview() {
    LoginDemoTheme {
        SingleInputField("2342343", "Phone Number")
    }
}