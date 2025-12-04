package com.pokeskies.cobblemonplaceholders.placeholders.types.species

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import net.minecraft.resources.ResourceLocation

class SpeciesLabelsHas : ServerPlaceholder {
    override fun handle(args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            )

        if (args.size < 2) {
            return GenericResult.invalid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.labelsHas.invalidFormat
            )
        }

        val (species, label) = if (args.size > 2) {
            PokemonSpecies.getByIdentifier(ResourceLocation.fromNamespaceAndPath(args[0].lowercase(), args[1].lowercase())) to args[2]
        } else {
            PokemonSpecies.getByName(args[0].lowercase()) to args[1]
        }

        if (species == null) {
            return GenericResult.invalid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            )
        }

        return GenericResult.valid(
            species.labels.any { it.equals(label, true) }
        )
    }

    override fun id(): List<String> = listOf("species_labels_has")
    class Options(
        @SerializedName("invalid_format")
        val invalidFormat: String = "Invalid arguments! Species and Label required."
    ) {
        override fun toString(): String {
            return "Options(invalidFormat='$invalidFormat')"
        }
    }
}
