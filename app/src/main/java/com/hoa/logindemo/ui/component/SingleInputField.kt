package com.hoa.logindemo.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.hoa.logindemo.ui.theme.Grey
import com.hoa.logindemo.ui.theme.LoginDemoTheme

@Composable
fun SingleInputField(
    value: MutableState<String>,
    label: String,
    modifier: Modifier = Modifier,
    onTextChanged: (() -> Unit)? = null
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        value = value.value,
        onValueChange = {
            value.value = it
            onTextChanged?.invoke()
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
        val phone = remember { mutableStateOf("345345") }
        SingleInputField(phone, "Phone Number")
    }
}