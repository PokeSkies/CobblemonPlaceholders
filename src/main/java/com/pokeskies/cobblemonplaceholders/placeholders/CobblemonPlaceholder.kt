package com.pokeskies.cobblemonplaceholders.placeholders

import io.github.miniplaceholders.api.Expansion

interface CobblemonPlaceholder {
    fun register(builder: Expansion.Builder)
}