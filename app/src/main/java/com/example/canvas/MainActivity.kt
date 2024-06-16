package com.example.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import com.example.canvas.ui.theme.CanvasTheme

@Composable
fun RoomCanvas() {
    // Definir los puntos para cada área y pasadillo
    val pointsArea1 = listOf(
        Offset(100f, 100f),
        Offset(350f, 100f),
        Offset(350f, 800f),
        Offset(100f, 800f)
    )

    val pointsPasadillo = listOf(
        Offset(350f, 200f),
        Offset(600f, 200f),
        Offset(600f, 700f),
        Offset(350f, 700f)
    )

    val pointsArea2 = listOf(
        Offset(600f, 100f),
        Offset(850f, 100f),
        Offset(850f, 800f),
        Offset(600f, 800f)
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        // Dibujar el área 1 usando drawRectangleWithLines
        drawRectangleWithLines(pointsArea1)

        // Dibujar el pasadillo usando drawRectangleWithRect
        drawRectangleWithRect(pointsPasadillo)

        // Dibujar el área 2 usando drawRectangleWithPath
        drawRectangleWithPath(pointsArea2)
    }
}

// Actividad principal de la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el modo edge-to-edge (pantalla completa)
        setContent {
            CanvasTheme { // Establece el tema para la composición
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        RoomCanvas() // Dibuja el canvas de la habitación
                    }
                }
            }
        }
    }
}

// Extensión de la función drawscope para dibujar un path cerrado
fun androidx.compose.ui.graphics.drawscope.DrawScope.drawRectangleWithPath(points: List<Offset>) {
    val path = Path().apply {
        moveTo(points[0].x, points[0].y)
        points.drop(1).forEach { point ->
            lineTo(point.x, point.y)
        }
        close() // Cierra el path conectando el último punto con el primero
    }

    drawPath(
        path = path,
        color = Color.Red,
        style = androidx.compose.ui.graphics.drawscope.Stroke(
            width = 5f
        )
    )
}

// Extensión de la función drawscope para dibujar líneas conectando puntos
fun androidx.compose.ui.graphics.drawscope.DrawScope.drawRectangleWithLines(points: List<Offset>) {
    for (i in points.indices) {
        val start = points[i]
        val end = points[(i + 1) % points.size] // El siguiente punto, conectando el último al primero
        drawLine(
            color = Color.Black,
            start = start,
            end = end,
            strokeWidth = 5f
        )
    }
}

// Extensión de la función drawscope para dibujar un rectángulo sólido
fun androidx.compose.ui.graphics.drawscope.DrawScope.drawRectangleWithRect(points: List<Offset>) {
    drawRect(
        color = Color.Green,
        topLeft = points[0], // Esquina superior izquierda del rectángulo
        size = Size(
            width = points[1].x - points[0].x, // Ancho del rectángulo
            height = points[3].y - points[0].y // Alto del rectángulo
        ),
        style = androidx.compose.ui.graphics.drawscope.Fill // Estilo de relleno
    )
}

// Previsualización de la función RoomCanvas
@Preview(showBackground = true)
@Composable
fun RoomCanvasPreview() {
    CanvasTheme {
        RoomCanvas()
    }
}

// Función composable para saludar
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

// Previsualización de la función Greeting
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CanvasTheme {
        Greeting("Android")
    }
}
