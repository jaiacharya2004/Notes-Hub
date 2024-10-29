package com.example.noteshub.screens.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import com.example.noteshub.viewmodel.Folder
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderDetailScreen(navController: NavController, folder: Folder) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF4A148C))
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(folder.name, color = Color.Black, fontSize = 24.sp) },
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
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color(0xFF4A148C))
            ) {
                items(folder.subFolders) { subFolder ->
                    FolderItem(subFolder) {
                        // Handle subfolder click (you can navigate further or perform another action)
                    }
                }
            }
        }
    }
}
