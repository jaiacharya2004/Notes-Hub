package com.example.noteshub.screens.home


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noteshub.viewmodel.AuthViewModel
import com.example.noteshub.viewmodel.Folder
import com.example.noteshub.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    authViewModel: AuthViewModel
) {
    // Handle back press to navigate up the folder hierarchy
    BackHandler(enabled = viewModel.currentFolder != null) {
        viewModel.navigateBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color(0xFFAB47BC)),
                title = { Text("Notes Hub") },
                navigationIcon = {
                    if (viewModel.currentFolder != null) {
                        IconButton(onClick = { viewModel.navigateBack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            if (viewModel.currentFolder?.subFolders?.isEmpty() == true) {
                FloatingActionButton(
                    onClick = {
                        // Handle file upload logic here
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Upload File")
                }
            }
        },
        content = { paddingValues ->
            FolderView(
                modifier = Modifier.padding(paddingValues),
                viewModel = viewModel
            )
        }
    )
}


@Composable
fun FolderView(modifier: Modifier = Modifier, viewModel: HomeViewModel) {
    val bcaFirstYear = Folder(
        name = "BCA 1st Year",
        subFolders = listOf(
            Folder(name = "C++"),
            Folder(name = "Maths"),
            Folder(name = "DBMS"),
            Folder(name = "Computer Fundamentals"),
            Folder(name = "Computer Networking"),
            Folder(name = "Fundamental of Programming"),
            Folder(name = "Other"),
        ),
    )

    val bcaSecondYear = Folder(
        name = "BCA 2nd Year",
        subFolders = listOf(
            Folder(name = "Computer Organization"),
            Folder(name = "Operating System"),
            Folder(name = "Java"),
            Folder(name = "Cloud Computing"),
            Folder(name = "Python"),
            Folder(name = "Other")
        ),
    )

    val bcaThirdYear = Folder(
        name = "BCA 3rd Year",
        subFolders = listOf(
            Folder(name = "Software Engineering"),
            Folder(name = "DSA"),
            Folder(name = "Android"),
            Folder(name = "PHP"),
            Folder(name = "Cyber Security"),
            Folder(name = "Other"),
        ),
    )

    val mcaFirstYear = Folder(
        name = "MCA 1st Year",
        subFolders = listOf(
            Folder(name = "All"),
        ),
    )

    val mcaSecondYear = Folder(
        name = "MCA 2nd Year",
        subFolders = listOf(
            Folder(name = "All"),
        ),
    )

    val mscFirstYear = Folder(
        name = "Msc 1st Year",
        subFolders = listOf(
            Folder(name = "All"),
        ),
    )

    val mscSecondYear = Folder(
        name = "Msc 2nd Year",
        subFolders = listOf(
            Folder(name = "All"),
        ),
    )



    val allCourses = listOf(bcaFirstYear, bcaSecondYear, bcaThirdYear, mcaFirstYear , mcaSecondYear,mscFirstYear ,mscSecondYear)

    val currentFolder = viewModel.currentFolder

    LazyColumn(modifier = modifier.padding(16.dp)) {
        if (currentFolder == null) {
            items(allCourses) { folder ->
                FolderCard(folder = folder) {
                    viewModel.navigateToFolder(folder)
                }
            }
        } else {
            items(currentFolder.subFolders) { folder ->
                FolderCard(folder = folder) {
                    viewModel.navigateToFolder(folder)
                }
            }
            items(currentFolder.files) { file ->
                FileCard(fileName = file)
            }
        }
    }
}

@Composable
fun FolderCard(folder: Folder, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE91E63))
    ) {
        Text(
            text = folder.name,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun FileCard(fileName: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB))
    ) {
        Text(
            text = fileName,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


