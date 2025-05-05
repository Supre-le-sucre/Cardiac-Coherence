package fr.supre.cardiac_coherence

import android.content.Context

enum class Sounds(val stringId: Int, val resId: Int) {

    WAVES(R.string.waves, R.raw.waves),
    RIVER(R.string.river, R.raw.river),
    FIREPLACE(R.string.fireplace, R.raw.fireplace),
    BIRDS(R.string.birds, R.raw.birdschirping),
    RAIN(R.string.rain, R.raw.rain),
    THUNDER(R.string.thunderstorm, R.raw.thunderstorm),
    CAT(R.string.cat, R.raw.cat)
}