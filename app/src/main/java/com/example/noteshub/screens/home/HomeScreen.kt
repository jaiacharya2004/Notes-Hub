package com.example.noteshub.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteshub.viewmodel.HomeViewModel
import com.example.noteshub.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel, authViewModel: AuthViewModel) {
    val folders by viewModel.folders.observeAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Folders", color = Color.White, fontSize = 24.sp) },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White)
                    }
                }
            )
        },
        containerColor = Color(0xFF4A148C)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF4A148C))
        ) {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(folders ?: emptyList(), key = { folder -> folder.name }) { folder ->
                    FolderItem(folder) {
                        onFolderClick(navController, folder)
                    }
                }
            }
        }
    }
}

@Composable
fun FolderItem(folder: Folder, onClick: () -> Unit) {
    // Define your FolderItem UI and logic here
    Text(
        text = folder.name,
        color = Color.White,
        modifier = Modifier.padding(8.dp)
    )
}

private fun onFolderClick(navController: NavController, folder: com.example.noteshub.viewmodel.Folder) {
    navController.navigate("folder/${folder.name}")
}

data class Folder(
    val name: String,
    val itemCount: Int,
    val size: String,
    val subFolders: List<Folder> = emptyList()
)
