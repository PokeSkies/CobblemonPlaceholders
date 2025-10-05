package com.pokeskies.cobblemonplaceholders.placeholders.types.species

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder

class SpeciesBaseStatsSpecialAttack : ServerPlaceholder {
    override fun handle(args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            )

        val species = PokemonSpecies.getByName(args[0].lowercase())
            ?: return GenericResult.invalid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            )

        return GenericResult.valid(species.baseStats[Stats.SPECIAL_ATTACK] ?: 0)
    }

    override fun id(): String = "species_basestats_spa"
}
