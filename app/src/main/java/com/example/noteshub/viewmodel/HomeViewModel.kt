


package com.example.noteshub.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.noteshub.viewmodel.Folder

class HomeViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val folderKey = "currentFolder"
    private val stackKey = "folderStack"
    var currentFolder by mutableStateOf<Folder?>(null)
    val folderStack = mutableListOf<Folder>()

    // SavedStateHandle is used to persist data
    private val handle = savedStateHandle


    fun navigateToFolder(folder: Folder) {
        currentFolder?.let { folderStack.add(it) }
        currentFolder = folder
    }

    fun navigateBack() {
        if (folderStack.isNotEmpty()) {
            currentFolder = folderStack.removeAt(folderStack.lastIndex)
        } else {
            currentFolder = null
        }
    }
}
