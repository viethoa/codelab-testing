package com.hoa.logindemo.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.hoa.logindemo.ui.extension.EditableTextState
import com.hoa.logindemo.ui.extension.rememberEditableTextState
import com.hoa.logindemo.ui.theme.Grey
import com.hoa.logindemo.ui.theme.LoginDemoTheme

@Composable
fun SingleInputField(
    modifier: Modifier = Modifier,
    label: String,
    state: EditableTextState,
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        value = state.value,
        onValueChange = {
            state.set(it)
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
        SingleInputField(label = "Phone Number", state = rememberEditableTextState("12312"))
    }
}