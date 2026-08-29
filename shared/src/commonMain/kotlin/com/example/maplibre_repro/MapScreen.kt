package com.example.maplibre_repro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.overlay.MapOverlay

/**
 * Reproduces https://github.com/maplibre/maplibre-compose/discussions/1125:
 * on Android, MaplibreMap composed with the default RenderMode.Surface inside conditional
 * (loading -> content) rendering stays invisible until the next redraw/touch.
 */
class MapScreen : Screen {
    @Composable
    override fun Content() {
        var loading by remember { mutableStateOf(true) }
        LaunchedEffect(Unit) {
            delay(1.seconds)
            loading = false
        }

        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Loading")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text("Another map below:")

                Spacer(Modifier.height(16.dp))

                MaplibreMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp)),
                    overlay = MapOverlay.None,
                )
            }
        }
    }
}
