package com.fatec.salafacil.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fatec.salafacil.ui.theme.Grey200
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.theme.WarningColor

@Composable
fun NotImplementedCard(
    modifier: Modifier = Modifier,
    title: String = "Funcionalidade não implementada",
    message: String = "Esta funcionalidade estará disponível em breve.",
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Grey200,
            contentColor = Grey500
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick ?: {}
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ícone grande
            Icon(
                imageVector = Icons.Default.Construction,
                contentDescription = "Em construção",
                modifier = Modifier.size(48.dp),
                tint = WarningColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Mensagem
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
        }
    }
}