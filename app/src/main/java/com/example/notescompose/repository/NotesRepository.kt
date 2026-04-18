package com.example.notescompose.repository

import com.example.notescompose.data.local.LocalNotesStaticList
import com.example.notescompose.data.local.Note
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class NotesRepository @Inject constructor(
    private val localNodesStatic: LocalNotesStaticList
) {

    fun fetchNotesFromDB() : Flow<List<Note>> = flow {
        delay(1000)
        emit(localNodesStatic.notes)
    }
}