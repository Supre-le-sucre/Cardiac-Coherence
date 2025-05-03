import android.R.attr.onClick
import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.supre.cardiac_coherence.Sounds
import kotlin.math.abs

class SetupSession(val modifier: Modifier) {

    @Composable
    fun InitialSetupParams(initialRespPerMin: Float, initialDuration: Int){
        var currRespPerMin by remember { mutableFloatStateOf(initialRespPerMin) }
        var currDuration by remember { mutableIntStateOf(initialDuration) }
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {

            Row {

                Slider(
                    value= currRespPerMin,
                    onValueChange = { currRespPerMin = it },
                    steps = 0,
                    valueRange=5f..10f,
                    enabled=true,
                    modifier = modifier.weight(1f).padding(top=24.dp, start=24.dp, end=8.dp)
                )

                Slider(
                    value= currDuration.toFloat(),
                    onValueChange = {currDuration = snapToNextMultiple(it, 30)},
                    steps = 0,
                    valueRange=300f..600f,
                    enabled=true,
                    modifier = modifier.weight(1f).padding(top=24.dp, start=8.dp, end=24.dp)
                )

            }
            val df = DecimalFormat("#.#")
            val respPerMinFormated: String = df.format(currRespPerMin)
            Text(
                text =
                    "$respPerMinFormated respirations par minute pendant $currDuration secondes",
                modifier = modifier//.padding(start=24.dp)
            )



            for (s in Sounds.entries) {

                Button(
                    onClick = {},
                    modifier = modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp)
                ) {
                    Text(s.stringToShow)
                }
            }

            ElevatedButton(
                onClick = {},
                modifier = modifier
            ) {
                Text("Commencer la séance")
            }



        }
    }

    /**
     * Makes the element snap to the next multiple given
     *
     * @param value the value to snap
     * @param next the value will snap on a multiple of "next"
     *
     * @return the closest integer multiple of next from value
     */
    private fun snapToNextMultiple(value: Float, next: Int): Int {
        println(value)
        var multiple = next

        while(multiple < value) { multiple += next }

        if (abs(value - multiple) < abs(value - multiple+next)) {
            return multiple
        }
        return multiple-next
    }


}