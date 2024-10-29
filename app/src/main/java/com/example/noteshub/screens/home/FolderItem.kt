package com.example.noteshub.screens.home


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteshub.viewmodel.Folder

@Composable
fun FolderItem(folder: Folder, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth() // Fill the width only
            .padding(vertical = 4.dp) // Add padding to give some space
            .background(color = Color(0xFF4A148C)) // Set background color here
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .background(color = Color(0xFF4A148C)) // Set background color here
                .padding(vertical = 4.dp)
                .clickable(onClick = onClick),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
//                verticalAlignment = Alignment.CenterVertically
            ) {
//            Icon(
//                painter = painterResource(id = R.drawable.folder_icon), // Replace with your actual folder icon resource
//                contentDescription = "Folder Icon", // Provide a content description for accessibility
//                modifier = Modifier.size(40.dp) // Set the size of the icon
//            )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = folder.name, fontSize = 18.sp, color = Color.Black)
                    Text(
                        text = "${folder.itemCount} items | ${folder.size}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

        }
    }
}


