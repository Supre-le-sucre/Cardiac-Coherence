package fr.supre.cardiac_coherence

import android.media.MediaPlayer
import android.content.Context
import java.util.Timer
import kotlin.concurrent.timer


class AudioTrack(context: Context, sound: Sounds, val nameId: String) {

    var fadeTimer: Timer? = null
    var volume: Float = 0f
    val player: MediaPlayer = MediaPlayer.create(context, sound.resId)


    /**
     * Sets the volume of the current audio track
     * Starts the player and stops it accordingly
     *
     * @param fadeDuration in ms
     *
     */
    fun setVolume(targetVolume: Float, fadeDuration: Int = 0) {

        // Cancelling any previous fade instruction.
        fadeTimer?.cancel()
        fadeTimer = null

        if(volume == 0f) player.start() // We can assume the player was not started

        if(fadeDuration == 0) {
            volume = targetVolume
            player.setVolume(volume, volume)
            if(targetVolume == 0f) player.stop()
            return
        }

        // Fade logic

        val initialVolume = volume
        val deltaVolume = targetVolume - initialVolume
        // If deltaVolume is positive, we fade in
        // If negative, we fade out. The formula remains the same anyway

        var msElapsed = 0
        fadeTimer = timer(name=nameId+"_fadeTask", initialDelay=0, period = 1, action = {
            volume = initialVolume + (deltaVolume/fadeDuration.toFloat())*msElapsed
            player.setVolume(volume, volume)
            msElapsed++

            if(fadeDuration <= msElapsed) {
                volume = targetVolume
                player.setVolume(volume, volume) // Fixing potential imprecision
                if(targetVolume == 0f) player.stop()
                // Task finished, removing timer
                fadeTimer = null
                cancel()
            }
        })

    }



}