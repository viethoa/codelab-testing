package com.hoa.logindemo.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.Flow

@Composable
fun rememberEditableTextState(defaultMessage: String = ""): EditableTextState =
    rememberSaveable(defaultMessage, saver = EditableTextState.Saver) {
        EditableTextState(defaultMessage)
    }

class EditableTextState(defaultMessage: String) {

    var value by mutableStateOf(defaultMessage)
        private set

    fun set(newMessage: String) {
        value = newMessage
    }

    fun clear() {
        value = ""
    }

    fun isNotEmpty(): Boolean = value.isNotEmpty()

    fun asFlow(): Flow<String> = snapshotFlow { value }

    companion object {
        val Saver: Saver<EditableTextState, *> = Saver(
            save = { it.value },
            restore = { EditableTextState(it) }
        )
    }
}