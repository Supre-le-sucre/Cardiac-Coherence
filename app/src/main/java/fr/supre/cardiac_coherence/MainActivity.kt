package fr.supre.cardiac_coherence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.supre.cardiac_coherence.ui.theme.CardiacCoherenceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CardiacCoherenceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val s = Session(6f, 300, arrayOf(Sounds.FIREPLACE, Sounds.RAIN))
                    InitialSetupParams(s)
                }
            }
        }
    }
}

@Composable
fun InitialSetupParams(s: Session){

    Row {
        Slider(value=s.respPerMin, onValueChange = { s.respPerMin = it }, steps = 5, valueRange=5f..10f, enabled=true)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Surface(color= Color.Red) {
        Text(
            text = "Hello $name!",
            modifier = modifier.padding(24.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    CardiacCoherenceTheme {
        val s = Session(6f, 300, arrayOf(Sounds.FIREPLACE, Sounds.RAIN))
        InitialSetupParams(s)
    }
}