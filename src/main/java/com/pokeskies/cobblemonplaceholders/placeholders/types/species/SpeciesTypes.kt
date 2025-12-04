package com.pokeskies.cobblemonplaceholders.placeholders.types.species

import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.config.ConfigManager
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.SpeciesSlotParser
import com.pokeskies.cobblemonplaceholders.utils.Utils

class SpeciesTypes : ServerPlaceholder {
    override fun handle(args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(
                ConfigManager.CONFIG.placeholders.species.invalidSpecies
            )

        val (species, slot) = SpeciesSlotParser.parseArgs(args)

        if (species == null) {
            return GenericResult.invalid(
                ConfigManager.CONFIG.placeholders.species.invalidSpecies
            )
        }

        val types = species.types.toList()

        if (slot != null) {
            if (slot !in 2 downTo 1)
                return GenericResult.invalid(
                    ConfigManager.CONFIG.placeholders.species.types.invalidSlot
                )

            return GenericResult.valid(
                if (slot <= types.size)
                    Utils.titleCase(types[slot - 1].name)
                else
                    ConfigManager.CONFIG.placeholders.species.types.emptySlot
            )
        }

        return GenericResult.valid(types.joinToString(", ") { Utils.titleCase(it.name) } )
    }

    override fun id(): List<String> = listOf("species_types")
    class Options(
        @SerializedName("invalid_slot")
        val invalidSlot: String = "Invalid type slot argument (1-2)!",
        @SerializedName("empty_slot")
        val emptySlot: String = "No Type"
    ) {
        override fun toString(): String {
            return "Options(invalidSlot='$invalidSlot', emptySlot='$emptySlot')"
        }
    }
}
