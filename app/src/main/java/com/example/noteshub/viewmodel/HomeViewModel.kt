package com.example.noteshub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class HomeViewModel : ViewModel() {
    // Backing property for the list of folders
    private val _folders = MutableLiveData<List<Folder>?>(emptyList())
    val folders: LiveData<List<Folder>?> get() = _folders // Expose only LiveData

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance() // Initialize FirebaseAuth


    init {
        loadFolders()
    }

    // Load initial dummy folders
    private fun loadFolders() {
        val dummyFolders = listOf(
            Folder("BCA 1st Year", 24, "432 MB", listOf(
                Folder("Maths", 30, "400 MB"),
                Folder("C++", 25, "300 MB"),
                Folder("DBMS", 40, "500 MB"),
                Folder("Computer Networks", 40, "500 MB"),
                Folder("Computer Fundamental", 40, "500 MB"),
                Folder("Fundamental of Programming", 40, "500 MB"),
            )),
            Folder("BCA 2nd Year", 126, "1.2 GB", listOf(
                Folder("Java", 30, "400 MB"),
                Folder("Python", 25, "300 MB"),
                Folder("Operating Systems", 40, "500 MB"),
                Folder("Computer Organization", 40, "500 MB"),
                Folder("Internet Programming", 40, "500 MB"),
                Folder("Cloud Computing", 40, "500 MB"),
            )),
            Folder("BCA 3rd Year", 126, "1.2 GB", listOf(
                Folder("Software Engineering", 30, "400 MB"),
                Folder("DSA", 25, "300 MB"),
                Folder("PHP", 40, "500 MB"),
                Folder("Android", 40, "500 MB"),
                Folder("Cyber Security", 40, "500 MB"),
            )),
            Folder("MCA 1st Year", 126, "1.2 GB", emptyList()),
            Folder("MCA 2nd Year", 126, "1.2 GB", emptyList()),
            Folder("MSc 1st Year", 126, "1.2 GB", emptyList()),
            Folder("MSc 2nd Year", 126, "1.2 GB", emptyList()),
        )
        _folders.value = dummyFolders
    }

    // Function to add a sub-folder to a parent folder
    fun addSubFolder(parentFolder: Folder, newFolder: Folder) {
        val currentList = _folders.value?.toMutableList() ?: mutableListOf()
        val updatedFolders = currentList.map { folder ->
            if (folder.name == parentFolder.name) {
                val updatedSubFolders = folder.subFolders + newFolder
                folder.copy(subFolders = updatedSubFolders) // Update subfolders
            } else {
                folder
            }
        }
        _folders.value = updatedFolders
    }

    // Function to delete a folder
    fun deleteFolder(folder: Folder) {
        val currentList = _folders.value?.toMutableList()
        currentList?.remove(folder)
        _folders.value = currentList
    }

    // Function to get a folder by name
    fun getFolderByName(name: String): Folder? {
        return _folders.value?.find { it.name == name }
    }


    // Optional: Reset or clear folders on sign-out
    fun clearFolders() {
        _folders.value = emptyList()
    }
}
