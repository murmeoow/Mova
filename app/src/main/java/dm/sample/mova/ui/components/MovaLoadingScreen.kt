package dm.sample.mova.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dm.sample.mova.ui.components.MovaCircularProgressBar

@Composable
fun MovaLoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .clickable(enabled = false, onClick = {}),
        contentAlignment = Alignment.Center
    ) {
        MovaCircularProgressBar()
    }
}