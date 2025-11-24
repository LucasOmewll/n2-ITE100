package com.fatec.salafacil.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fatec.salafacil.ui.theme.Brand100
import com.fatec.salafacil.ui.theme.Brand400

@Composable
fun FormProgressBar(
    questionIndex: Int,
    totalQuestionsCount: Int
) {
    val progress by animateFloatAsState(
        targetValue = (questionIndex + 1 ) / totalQuestionsCount.toFloat()
    )

    LinearProgressIndicator(
        progress = {
            progress
        },
        modifier = Modifier.fillMaxWidth(),
        color = Brand400,
        trackColor = Brand100,
        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
    )
}

@Preview
@Composable
fun FormProgressBarPreview() {
    MaterialTheme {
        Surface {
            FormProgressBar(1, 5)
        }
    }
}