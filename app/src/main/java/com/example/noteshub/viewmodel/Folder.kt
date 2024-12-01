package com.example.noteshub.viewmodel


data class Folder(
    val name: String,
    val subFolders: List<Folder> = emptyList(),
    val files: List<String> = emptyList()
)
