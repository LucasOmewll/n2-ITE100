package com.fatec.salafacil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.ui.theme.Brand500
import com.fatec.salafacil.R
import com.fatec.salafacil.ui.translations.PT
import com.fatec.salafacil.ui.components.SecondaryButton
import com.fatec.salafacil.ui.theme.White

@Composable
fun WelcomeScreen(onNavigateToLogin: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Brand500
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 20.dp,
                    bottom = 60.dp,
                    start = 20.dp,
                    end = 20.dp
                ),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .height(320.dp)
                    .width(320.dp),

                painter = painterResource(R.drawable.logo_large),
                contentDescription = "Logo do SalaFácil"
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = PT.welcome_title,
                style = MaterialTheme.typography.displayLarge,
                color = White
            )

            Text(
                text = PT.welcome_subtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = White
            )

            Spacer(modifier = Modifier.height(20.dp))

            SecondaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = PT.welcome_start_button,
                onClick = { onNavigateToLogin() }
            )
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen({})
}