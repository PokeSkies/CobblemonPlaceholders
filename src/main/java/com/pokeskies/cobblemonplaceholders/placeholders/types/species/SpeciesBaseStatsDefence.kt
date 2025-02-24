package com.pokeskies.cobblemonplaceholders.placeholders.types.species

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import net.kyori.adventure.text.Component

class SpeciesBaseStatsDefence : ServerPlaceholder {
    override fun handle(args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            ))

        val species = PokemonSpecies.getByName(args[0].lowercase())
            ?: return GenericResult.invalid(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            ))

        return GenericResult.valid(Component.text(species.baseStats[Stats.DEFENCE] ?: 0))
    }

    override fun id(): String = "species_basestats_def"
}
