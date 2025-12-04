package com.pokeskies.cobblemonplaceholders.placeholders.types.species

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.pokeskies.cobblemonplaceholders.config.ConfigManager
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import net.minecraft.resources.ResourceLocation

class SpeciesBaseStatsAttack : ServerPlaceholder {
    override fun handle(args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(
                ConfigManager.CONFIG.placeholders.species.invalidSpecies
            )

        val species = if (args.size > 1) {
            PokemonSpecies.getByIdentifier(ResourceLocation.fromNamespaceAndPath(args[0].lowercase(), args[1].lowercase()))
        } else {
            PokemonSpecies.getByName(args[0].lowercase())
        } ?: return GenericResult.invalid(
            ConfigManager.CONFIG.placeholders.species.invalidSpecies
        )

        return GenericResult.valid(species.baseStats[Stats.ATTACK] ?: 0)
    }

    override fun id(): List<String> = listOf("species_basestats_atk")
}
