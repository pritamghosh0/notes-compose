package com.example.notescompose.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoteDetailScreen(noteId: String){
    Surface {
        Column(modifier = Modifier.padding(start = 24.dp, top = 20.dp, end = 24.dp)) {
            Text(text = "Note details Screen, node Id = $noteId")
        }
    }
}

object NoteDetails{
    const val route = "noteDetails"
    const val noteIdArg = "noteId"
}