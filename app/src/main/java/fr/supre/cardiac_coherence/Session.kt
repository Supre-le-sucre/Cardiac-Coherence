package fr.supre.cardiac_coherence


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
class Session(var respPerMin: Float,  var duration: Int, var sounds: Array<Sounds>) {

    constructor(respPerMin: Float, duration: Int) : this(respPerMin, duration, emptyArray())

}