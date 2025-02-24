package com.pokeskies.cobblemonplaceholders.placeholders.types.species

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.Utils
import net.kyori.adventure.text.Component

class SpeciesTypes : ServerPlaceholder {
    override fun handle(args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            ))

        val species = PokemonSpecies.getByName(args[0].lowercase())
            ?: return GenericResult.invalid(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            ))

        val types = species.types.toList()

        if (args.size > 1) {
            val typeSlot = args.getOrNull(1)?.toIntOrNull()
            if (typeSlot == null || typeSlot !in 2 downTo 1)
                return GenericResult.invalid(Component.text(
                    CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.types.invalidSlot
                ))

            return GenericResult.valid(Component.text(
                if (typeSlot <= types.size)
                    Utils.titleCase(types[typeSlot - 1].name)
                else
                    CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.types.emptySlot
            ))
        }

        return GenericResult.valid(Component.text(types.joinToString(", ") { Utils.titleCase(it.name) } ))
    }

    override fun id(): String = "species_types"

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
