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
    var sessionTerminated = false


    val soundPlayer: ArrayList<MediaPlayer> = ArrayList<MediaPlayer>()

    fun start() {
        println("Starting $respPerMin resp/min for $duration using $sounds")
        for (s in sounds) {
            soundPlayer.add(MediaPlayer.create(context, s.resId))
        }
        val msDelay = 100
        var msElapsed = 0
        var msElapsedSinceSignal = -1
        var audioPlaying = false
        var respCycleStarted = false
        this.sessionTimer = timer(name="session", initialDelay=100, period = 100, action =
            {
                msElapsed++;
                if(!soundPlayer.isEmpty() && msElapsed <= msDelay) {
                    // Fade in
                    for (mp in soundPlayer) {
                        mp.setVolume(
                            ((1/msDelay.toFloat())*msElapsed).toFloat(),
                            ((1/msDelay.toFloat())*msElapsed).toFloat())
                        if(!audioPlaying) {
                            mp.start()
                        }
                    }
                    audioPlaying = true

                }
                else {
                    if (!respCycleStarted) {
                        respCycle()
                        respCycleStarted = true
                    }
                }
                if(msElapsed >= duration*10) {
                    sessionTerminated=true
                }

                if(sessionTerminated) {

                    if(msElapsedSinceSignal == -1) msElapsedSinceSignal = 0
                    // Start fadeout
                    for (mp in soundPlayer) {
                        mp.setVolume(
                            (-(1/msDelay.toFloat())*msElapsedSinceSignal).toFloat() +1f,
                            (-(1/msDelay.toFloat())*msElapsedSinceSignal).toFloat() +1f)
                    }
                    if(msElapsedSinceSignal >= msDelay*2) {
                        for(mp in soundPlayer) {
                            mp.stop()
                        }
                        cancel()
                    }
                    msElapsedSinceSignal++
                }


            })
    }

    fun stop() {
        if(!sessionTerminated) println("Session has ended prematurely")
        sessionTerminated = true
    }

    private fun respCycle(): Timer {
        val durationOfOneResp = (60/respPerMin)/2
        val timeOfCycle: Long = (durationOfOneResp*1000).toLong()

        val inspireSound = MediaPlayer.create(context, R.raw.inspire)
        inspireSound.setVolume(0.3f, 0.3f)
        val expireSound = MediaPlayer.create(context, R.raw.expire)
        expireSound.setVolume(0.3f, 0.3f)

        var timeLeft = duration.toFloat()

        var inspiration = true
        val cycleTimer = timer(name="respCycle", period = timeOfCycle, action= {
            if(inspiration) {

                inspireSound.start()
                inspiration = false
            }

            else {

                expireSound.start()
                inspiration = true
            }
            timeLeft-=durationOfOneResp

            if(sessionTerminated && inspiration) {
                cancel()
            }
        })
        return cycleTimer

    }


}