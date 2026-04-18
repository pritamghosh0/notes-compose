package com.example.notescompose.ui.details

import androidx.lifecycle.ViewModel
import com.example.notescompose.repository.NotesRepository
import javax.inject.Inject

class NoteDetailsViewModel @Inject constructor(
    private val repository: NotesRepository
): ViewModel() {
}