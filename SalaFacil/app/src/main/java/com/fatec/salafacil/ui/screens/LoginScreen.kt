package com.fatec.salafacil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.R
import com.fatec.salafacil.ui.PT
import com.fatec.salafacil.ui.theme.Brand500
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.theme.White

@Composable
fun LoginScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),

                painter = painterResource(R.drawable.login_img),
                contentDescription = "Pessoas em sala de reunião",
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(
                        top = 10.dp,
                        bottom = 60.dp,
                        start = 10.dp,
                        end = 10.dp
                    )
                    .fillMaxSize(),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = PT.login_title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Grey500
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}