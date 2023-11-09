package com.pokeskies.cobblemonplaceholders.placeholders

import io.github.miniplaceholders.api.Expansion
import io.github.miniplaceholders.api.placeholder.AudiencePlaceholder

interface CobblemonAudiencePlaceholder : AudiencePlaceholder {
    fun register(builder: Expansion.Builder)
}