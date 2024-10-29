package com.example.noteshub.viewmodel


data class Folder(
    val name: String,
    val itemCount: Int,
    val size: String,
    val subFolders: List<Folder> = emptyList()  // Ensure this is not nullable
)
