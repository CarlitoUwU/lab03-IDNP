package com.example.lab03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CircleSizeAnimatorScreen()
                }
            }
        }
    }
}

@Composable
fun CircleSizeAnimatorScreen() {
    val smallSize = 100.dp
    val largeSize = 250.dp

    var isLarge by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val animatedSize by animateDpAsState(
        targetValue = if (isLarge) largeSize else smallSize,
        animationSpec = tween(durationMillis = 450)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.wrapContentSize(Alignment.Center)) {
            Canvas(modifier = Modifier.size(animatedSize)) {
                drawCircleInCanvas(this)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch {
                    // agranda
                    isLarge = true
                    delay(600)
                    // reduce
                    isLarge = false
                }
            }
        ) {
            Text("Pulso")
        }
    }
}

private fun drawCircleInCanvas(drawScope: DrawScope) {
    val canvasSize = drawScope.size
    val radius = (minOf(canvasSize.width, canvasSize.height) / 2f)
    val center = Offset(x = canvasSize.width / 2f, y = canvasSize.height / 2f)

    drawScope.drawCircle(
        color = Color(0xFF1E88E5),
        radius = radius,
        center = center
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCircleSizeAnimatorScreen() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            CircleSizeAnimatorScreen()
        }
    }
}
