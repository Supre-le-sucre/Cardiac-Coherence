package fr.supre.cardiac_coherence

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import fr.supre.cardiac_coherence.ui.theme.CardiacCoherenceTheme


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CardiacCoherenceTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    // Can be removed, serves as title
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary
                            ),
                            title= {
                                Text("Cohérence Cardiaque")
                            }
                        )
                    }
                ) { innerPadding ->



                    val setupSession = SetupSession(this, Modifier.padding(innerPadding))
                    setupSession.InitialSetupParams(6f, 300)
                }
            }
        }
    }


}


/**
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Surface(color= Color.Red) {
        Text(
            text = "Hello $name!",
            modifier = modifier.padding(24.dp)
        )
    }
}**/


/**@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    CardiacCoherenceTheme {
        val setupSession = SetupSession(Modifier)
        setupSession.InitialSetupParams(5f, 300)
    }
}**/