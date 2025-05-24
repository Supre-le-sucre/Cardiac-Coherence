package fr.supre.cardiac_coherence

import android.content.Context
import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.abs

class SetupSession(val context: Context, val modifier: Modifier) {

    val soundSelected= mutableStateMapOf<Sounds, Boolean>()


    @Composable
    fun InitialSetupParams(initialRespPerMin: Float, initialDuration: Int){
        var currRespPerMin by remember { mutableFloatStateOf(initialRespPerMin) }
        var currDuration by remember { mutableIntStateOf(initialDuration) }
        var currentSession by remember { mutableStateOf<Session?>(null) }
        Column(
            modifier = modifier.wrapContentSize().padding(start=32.dp, end=32.dp, top=32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Column(
                modifier = Modifier.padding(bottom=24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Slider(
                    value = currRespPerMin,
                    onValueChange = { currRespPerMin = it },
                    steps = 0,
                    valueRange = 5f..10f,
                    enabled = currentSession == null,
                )
                val df = DecimalFormat("#.#")
                val respPerMinFormated: String = df.format(currRespPerMin)
                Text(
                    text =
                        respPerMinFormated + " " + context.getString(R.string.resp_per_min),
                )

                Slider(
                    value = currDuration.toFloat(),
                    onValueChange = { currDuration = snapToNextMultiple(it) },
                    steps = 0,
                    valueRange = 120f..1200f,
                    enabled = currentSession == null,
                )

                Text(
                    text = context.getString(R.string.during) + " " + prettyFormatSeconds(currDuration)
                )

            }


            Column(Modifier.padding(bottom=24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(context.getString(R.string.with_the_sounds))


                for (s in Sounds.entries) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = (soundSelected[s] == true),
                            onCheckedChange = { soundSelected[s] = it },
                            enabled = currentSession == null,
                        )

                        Text(context.getString(s.stringId))
                    }
                }
            }

            Row(modifier = Modifier.padding(bottom = 24.dp)) {
                ElevatedButton(
                    enabled = currentSession == null,
                    modifier = Modifier.weight(1f).padding(end=8.dp),
                    onClick = {
                        val df = DecimalFormat("#.#")
                        val respPerMinFormated: Float = df.format(currRespPerMin).toFloat()
                        currentSession = Session(
                            context,
                            respPerMinFormated,
                            currDuration,
                            soundSelected.filterKeys { soundSelected[it] == true }.keys
                        )
                        currentSession?.start()


                    },

                ) {
                    Text(context.getString(R.string.start))
                }
                ElevatedButton(
                    enabled = currentSession != null,
                    modifier = Modifier.weight(1f).padding(start=8.dp),
                    onClick = {

                        currentSession?.stop()
                        currentSession = null

                    },

                ) {
                    Text(context.getString(R.string.stop))
                }
            }

            Text(context.getString(R.string.instruction_breath_in))
            Text(context.getString(R.string.instruction_breath_out))
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
    private fun snapToNextMultiple(value: Float, next: Int= 15): Int {
        var multiple = next

        while(multiple < value) { multiple += next }

        if (abs(value - multiple) < abs(value - multiple + next)) {
            return multiple
        }
        return multiple-next
    }

    /**
     * @return second conversion in minutes and seconds
     */
    private fun prettyFormatSeconds(value: Int): String {
        val minutes = value/60
        val seconds = value%60

        var secondsString = "..."
        if(seconds != 0) secondsString = " " + context.getString(R.string.and) + " " +
                seconds + " "+ context.getString(R.string.seconds) +"..."
        return ""+ minutes + " " + context.getString(R.string.minutes) + secondsString

    }

}