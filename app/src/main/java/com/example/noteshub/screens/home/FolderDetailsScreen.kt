package com.example.noteshub.screens.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import com.example.noteshub.viewmodel.Folder
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteshub.viewmodel.HomeViewModel
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderDetailScreen(
    navController: NavController,
    folder: Folder,
    homeViewModel: HomeViewModel // Pass HomeViewModel to handle subfolder creation
) {
    val showDialog = remember { mutableStateOf(false) }
    val newFolderName = remember { mutableStateOf("") }

    // MutableStateList to hold subfolders locally
    val subFoldersState = remember { mutableStateListOf(*folder.subFolders.toTypedArray()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF4A148C))
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(folder.name, color = Color.White, fontSize = 24.sp) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Handle settings */ }) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }
                )
            },
            containerColor = Color(0xFF4A148C),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDialog.value = true },
                    containerColor = Color(0xFFBB86FC)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Subfolder", tint = Color.White)
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color(0xFF4A148C))
            ) {
                items(subFoldersState) { subFolder ->
                    FolderItem(subFolder) {
                        // Handle subfolder click (navigate further or perform another action)
                    }
                }
            }
        }

        // Dialog for adding a new subfolder
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text("New Subfolder") },
                text = {
                    OutlinedTextField(
                        value = newFolderName.value,
                        onValueChange = { newFolderName.value = it },
                        label = { Text("Subfolder Name") }
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val newSubFolder = Folder(newFolderName.value, 0, "0 MB")
                            subFoldersState.add(newSubFolder) // Add to local state
                            homeViewModel.addSubFolder(folder.name, newFolderName.value) // Save to ViewModel
                            newFolderName.value = "" // Clear the input
                            showDialog.value = false // Dismiss the dialog
                        }
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog.value = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
