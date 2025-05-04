package fr.supre.cardiac_coherence

enum class Sounds(val stringToShow: String, val resId: Int) {

    WAVES("Vagues", R.raw.waves),
    RIVER("Rivière", R.raw.river),
    FIREPLACE("Feu de bois", R.raw.fireplace),
    BIRDS("Chants d'oiseaux", R.raw.birdschirping),
    RAIN("Pluie", R.raw.rain),
    THUNDER("Orage", R.raw.thunderstorm),
    CAT("Ronronnement", R.raw.cat)
}