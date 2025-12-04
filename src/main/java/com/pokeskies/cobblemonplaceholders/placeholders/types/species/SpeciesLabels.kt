package com.pokeskies.cobblemonplaceholders.placeholders.types.species

import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.SpeciesSlotParser

class SpeciesLabels : ServerPlaceholder {
    override fun handle(args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            )

        val (species, slot) = SpeciesSlotParser.parseArgs(args)

        if (species == null) {
            return GenericResult.invalid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            )
        }


        if (slot != null) {
            if (slot <= 0) {
                return GenericResult.valid(
                    CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.labels.invalidSlot
                )
            }

            return GenericResult.valid(
                if (slot <= species.labels.size)
                    species.labels.toList()[slot - 1]
                else
                    CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.labels.emptySlot
            )
        }

        if (species.labels.isEmpty())
            return GenericResult.valid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.labels.emptyList
            )

        return GenericResult.valid(
            species.labels.joinToString(", ")
        )
    }

    override fun id(): List<String> = listOf("species_labels")
    class Options(
        @SerializedName("invalid_slot")
        val invalidSlot: String = "Invalid labels slot argument (1+)!",
        @SerializedName("empty_list")
        val emptyList: String = "No Labels",
        @SerializedName("empty_slot")
        val emptySlot: String = "Empty Label"
    ) {
        override fun toString(): String {
            return "Options(invalidSlot='$invalidSlot', emptyList='$emptyList', emptySlot='$emptySlot')"
        }
    }
}
