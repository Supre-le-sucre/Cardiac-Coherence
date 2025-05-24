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
    var respTimer: Timer? = null
    var sessionTerminated = false


    val audioTrack: ArrayList<AudioTrack> = ArrayList<AudioTrack>()

    fun start() {
        println("Starting $respPerMin resp/min for $duration using $sounds")

        var delayBeforeRespCycle: Long = 15 // in seconds
        if (sounds.isEmpty()) delayBeforeRespCycle=3

        for (s in sounds) {
            var track = AudioTrack(context, s, s.name)
            audioTrack.add(track)
            track.setVolume(1f, 1000 * 10) // 10 seconds
        }

        var secondsElapsed = 0
        var respCycleStarted = false

        this.sessionTimer = timer(name="session", initialDelay=1000*delayBeforeRespCycle,
            period = 1000, action =
            {
                if(sessionTerminated) {
                    // Start fadeout
                    for (track in audioTrack) {
                        track.setVolume(0f, 1000*20) // 20 seconds
                    }
                    sessionTimer = null
                    cancel()
                }

                if(secondsElapsed >= duration) {
                    sessionTerminated=true
                }

                if (!respCycleStarted) {
                    respCycle()
                    respCycleStarted = true
                }

                secondsElapsed++

            })
    }

    fun stop() {
        println("Session has ended prematurely")
        for (track in audioTrack) {
            track.setVolume(0f, 1000) // 1 second
        }
        sessionTimer?.cancel()
        sessionTimer = null
        respTimer?.cancel()
        respTimer = null
        sessionTerminated = true
    }

    private fun respCycle() {
        val durationOfOneResp = (60/respPerMin)/2
        val timeOfCycle: Long = (durationOfOneResp*1000).toLong()

        val inspireSound = MediaPlayer.create(context, R.raw.inspire)
        inspireSound.setVolume(0.1f, 0.1f)
        val expireSound = MediaPlayer.create(context, R.raw.expire)
        expireSound.setVolume(0.1f, 0.1f)


        var inspiration = true
        respTimer = timer(name="respCycle", period = timeOfCycle, action= {
            if(inspiration) {

                inspireSound.start()
                inspiration = false
            }

            else {

                expireSound.start()
                inspiration = true
            }

            if(sessionTerminated && inspiration) {
                respTimer = null
                cancel()
            }
        })


    }


}