package com.pokeskies.cobblemonplaceholders.placeholders.types.species

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import net.minecraft.resources.ResourceLocation

class SpeciesMaleRatio : ServerPlaceholder {
    override fun handle(args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            )

        val species = if (args.size > 1) {
            PokemonSpecies.getByIdentifier(ResourceLocation.fromNamespaceAndPath(args[0].lowercase(), args[1].lowercase()))
        } else {
            PokemonSpecies.getByName(args[0].lowercase())
        } ?: return GenericResult.invalid(
            CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
        )

        return GenericResult.valid(species.maleRatio)
    }

    override fun id(): String = "species_maleratio"
}
