package com.example.notescompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notescompose.data.local.Note
import com.example.notescompose.di.IoDispatcher
import com.example.notescompose.repository.NotesRepository
import com.example.notescompose.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _notes: MutableStateFlow<Result<List<Note>>> = MutableStateFlow(Result.Loading)
    val notes: StateFlow<Result<List<Note>>> = _notes

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        try {
            viewModelScope.launch(ioDispatcher) {
                repository.fetchNotesFromDB().collect {
                    if (it is List<Note>) {
                        _notes.value = Result.Success(it)
                    } else {
                        _notes.value = Result.Error(Exception("Something went wrong"))
                    }
                }
            }
        } catch (e: Exception) {
            _notes.value = Result.Error(e)
        }
    }

}