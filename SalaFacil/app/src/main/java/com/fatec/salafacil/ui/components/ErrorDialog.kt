package com.fatec.salafacil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.fatec.salafacil.ui.theme.ErrorColor
import com.fatec.salafacil.ui.theme.Grey400
import com.fatec.salafacil.ui.theme.Shapes

@Composable
fun ErrorDialog(
    errorMessage: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit = onDismissRequest,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(16.dp)
                .shadow(elevation = 4.dp)
                .background(MaterialTheme.colorScheme.surface, shape = Shapes.medium),
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Erro",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = ErrorColor
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    PrimaryButton(
                        text = "OK",
                        onClick = {
                            onConfirm()
                            onDismissRequest()
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ErrorDialogPreview() {
    MaterialTheme {
        Surface {
            ErrorDialog(
                errorMessage = "TODO()",
                onDismissRequest = {},
                onConfirm = {  },
            )
        }
    }
}