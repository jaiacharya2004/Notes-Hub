package com.example.noteshub.screens.auth

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteshub.R
import com.google.android.play.core.integrity.x

@Composable
fun SettingsScreen(onSignOut: () -> Unit) {


    val offsetX = remember { Animatable(300f) } // Start off-screen to the right

    LaunchedEffect(Unit) {
        // Animate the text sliding in from the right
        while (true) {
            offsetX.animateTo(
                targetValue = 30f,
                animationSpec = tween(durationMillis = 500)
            )
            offsetX.animateTo(
                targetValue = -30f,
                animationSpec = tween(durationMillis = 500)
            )
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4A148C))
            .padding(top=60.dp,),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    )

     {
         Text(
             "Thanks for Using this App",
             color =Color.White,
             fontSize = 20.sp,
             modifier = Modifier
             .offset(x = offsetX.value.dp)

         )

         Spacer(modifier = Modifier.height(200.dp))

         Image(
             painter = painterResource(id = R.drawable.thank_you_card_concept_illustration_114360_13433),
             contentDescription = "Thanks ",
             modifier = Modifier.size(192.dp)
         )


        Spacer(modifier = Modifier.height(180.dp))
        Button(onClick = onSignOut) {
            Text("Sign Out")
        }
    }
}
