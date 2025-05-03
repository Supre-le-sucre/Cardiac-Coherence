package fr.supre.cardiac_coherence

import android.content.Context
import android.media.MediaPlayer
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
                        var mediaPlayer = MediaPlayer.create(context, R.raw.waves)
                        mediaPlayer.start()
                        println("audio started")
                        audioStarted = true
                    }
                }

            })
    }

    fun stop() {
        println("Session has ended prematurely")
        sessionTimer?.cancel()
    }

}