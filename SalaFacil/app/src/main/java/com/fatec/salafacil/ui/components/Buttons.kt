package com.fatec.salafacil.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.ui.theme.Brand400
import com.fatec.salafacil.ui.theme.ErrorColor
import com.fatec.salafacil.ui.theme.Grey100
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.theme.Shapes
import com.fatec.salafacil.ui.theme.White


@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Brand400,
            contentColor = White
        ),
        modifier = modifier,
        shape = Shapes.small
    ) {
        Text(text)
    }
}

@Composable
fun SecondaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Grey500,
            contentColor = Grey100
        ),
        modifier = modifier,
        shape = Shapes.small
    ) {
        Text(text)
    }
}

@Composable
fun DangerButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = ErrorColor,
            contentColor = White
        ),
        modifier = modifier,
        shape = Shapes.small
    ) {
        Text(text)
    }
}

@Composable
fun OutlinedPrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    borderWidth: Dp = 1.dp
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Brand400
        ),
        // usa a versão com enabled para ter a opacidade correta quando desabilitado
        border = ButtonDefaults.outlinedButtonBorder(enabled = enabled),
        modifier = modifier,
        shape = Shapes.small
    ) {
        Text(text)
    }
}

@Composable
fun OutlinedSecondaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Grey100
        ),
        border = ButtonDefaults.outlinedButtonBorder(enabled = enabled),
        modifier = modifier,
        shape = Shapes.small
    ) {
        Text(text)
    }
}


@Composable
fun OutlinedDangerButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = ErrorColor
        ),
        border = ButtonDefaults.outlinedButtonBorder(enabled = enabled),
        modifier = modifier,
        shape = Shapes.small
    ) {
        Text(text)
    }
}

@Preview(showBackground = true, name = "Filled + Outlined - Enabled")
@Composable
fun ButtonsPreviewEnabled() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PrimaryButton(text = "Primary", onClick = {}, enabled = true)
        OutlinedPrimaryButton(text = "Outlined Primary", onClick = {}, enabled = true)
        Spacer(modifier = Modifier.height(6.dp))
        SecondaryButton(text = "Secondary", onClick = {}, enabled = true)
        OutlinedSecondaryButton(text = "Outlined Secondary", onClick = {}, enabled = true)
        Spacer(modifier = Modifier.height(6.dp))
        DangerButton(text = "Danger", onClick = {}, enabled = true)
        OutlinedDangerButton(text = "Outlined Danger", onClick = {}, enabled = true)
    }
}

@Preview(showBackground = true, name = "Filled + Outlined - Disabled")
@Composable
fun ButtonsPreviewDisabled() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PrimaryButton(text = "Primary", onClick = {}, enabled = false)
        OutlinedPrimaryButton(text = "Outlined Primary", onClick = {}, enabled = false)
        Spacer(modifier = Modifier.height(6.dp))
        SecondaryButton(text = "Secondary", onClick = {}, enabled = false)
        OutlinedSecondaryButton(text = "Outlined Secondary", onClick = {}, enabled = false)
        Spacer(modifier = Modifier.height(6.dp))
        DangerButton(text = "Danger", onClick = {}, enabled = false)
        OutlinedDangerButton(text = "Outlined Danger", onClick = {}, enabled = false)
    }
}