package fr.supre.cardiac_coherence

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Timer
import kotlin.concurrent.timer


/**
 * A cardiac Coherence Session.
 *
 * This class should be constructed when the application first launches
 * its parameters should be modified according to user input
 *
 * @param respPerMin The number of respiration per minute
 * @param duration The duration of the session in seconds (should be at least 300 seconds (5 minutes)
 * @param sounds The sound to be played during the session (can be empty)
 *
 */
class Session(val context: Context, var respPerMin: Float,  var duration: Int, var sounds: Set<Sounds>) {

    var sessionTimer: Timer? = null

    @RequiresApi(Build.VERSION_CODES.R)
    val contextWithTag = context.createAttributionContext("media_playback")


    val mediaPlayers: ArrayList<MediaPlayer> = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        arrayListOf(
            MediaPlayer.create(contextWithTag, R.raw.inspire),
            MediaPlayer.create(contextWithTag, R.raw.expire)
        )
    } else {
        arrayListOf(
            MediaPlayer.create(context, R.raw.inspire),
            MediaPlayer.create(context, R.raw.expire)
        )
    }

    fun start() {
        println("Starting $respPerMin resp/min for $duration using $sounds")
        var msElapsed = -30
        var audioStarted = false
        this.sessionTimer = timer(name="session", period = 100, action =
            {
                msElapsed++;
                if(msElapsed < 0) {
                    // Delaying logic

                }
                else {

                    if(!audioStarted) {
                        respCycle()
                        audioStarted = true
                    }
                }

            })
    }

    fun stop() {
        println("Session has ended prematurely")
        sessionTimer?.cancel()
    }

    private fun respCycle() {
        val durationOfOneResp = (60/respPerMin)/2
        val timeOfCycle: Long = (durationOfOneResp*1000).toLong()
        var timeLeft = duration.toFloat()

        var inspiration = true
        val cycleTimer = timer(name="respCycle", period = timeOfCycle, action= {
            println("running")
            if(inspiration) {

                mediaPlayers[0].start()
                inspiration = false
            }

            else {

                mediaPlayers[1].start()
                inspiration = true
            }
            timeLeft-=durationOfOneResp

            if(timeLeft < 0 && inspiration) {
                cancel()
                println("ended $timeLeft")
            }
        })

    }


}