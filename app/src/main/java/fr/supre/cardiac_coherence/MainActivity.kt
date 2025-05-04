package fr.supre.cardiac_coherence

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.supre.cardiac_coherence.ui.theme.CardiacCoherenceTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CardiacCoherenceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val setupSession = SetupSession(this, Modifier.padding(innerPadding))

                    setupSession.InitialSetupParams(5f, 300)
                }
            }
        }
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


/**@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    CardiacCoherenceTheme {
        val setupSession = SetupSession(Modifier)
        setupSession.InitialSetupParams(5f, 300)
    }
}**/