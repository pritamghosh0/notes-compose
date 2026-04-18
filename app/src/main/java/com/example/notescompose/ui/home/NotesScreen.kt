package com.example.notescompose.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notescompose.data.local.Note
import com.example.notescompose.repository.Result
import com.example.notescompose.ui.components.NoteListItem

@Composable
fun NotesScreen(onItemClick:(nodeId: String) -> Unit, onAddNoteClick: () -> Unit,
                modifier: Modifier = Modifier,
                viewModel: NotesViewModel = hiltViewModel()
 ){
    val notesResult by viewModel.notes.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ){ padding ->
        Column(modifier = Modifier.padding(padding).padding(horizontal = 24.dp, vertical = 20.dp)) {
            when(val result = notesResult){
                is Result.Loading -> Box(modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is Result.Success<*> -> NotesList(notes = result.data as List<Note>, onItemClick = onItemClick)
                is Result.Error -> ShowError(snackbarHostState, result.exception)
            }
        }
    }
}

@Composable
fun NotesList(notes: List<Note>, onItemClick: (String) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = notes,
            key = { note-> note.id }) { note ->
            NoteListItem(note = note, onItemClick = onItemClick)
        }
    }
}

@Composable
fun ShowError(snackbarHostState: SnackbarHostState, exception: Exception){
    Text("Getting your notes...")
    LaunchedEffect(Unit) {
        snackbarHostState.showSnackbar(exception.message ?: "Something went wrong")
    }
}
object Notes{
    const val route = "notes"
}
