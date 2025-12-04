package com.pokeskies.cobblemonplaceholders.placeholders.types.species

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.pokemon.abilities.HiddenAbilityType
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.Utils
import net.minecraft.resources.ResourceLocation

class SpeciesAbilities : ServerPlaceholder {
    override fun handle(args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            )

        val lastArgInt = args.lastOrNull()?.also {
            it.toIntOrNull() != null || it.equals("H", true)
        }

        val species = if (lastArgInt != null) {
            if (args.size == 2) {
                PokemonSpecies.getByName(args[0].lowercase())
            } else {
                PokemonSpecies.getByIdentifier(ResourceLocation.fromNamespaceAndPath(args[0].lowercase(), args[1].lowercase()))
            }
        } else {
            if (args.size == 1) {
                PokemonSpecies.getByName(args[0].lowercase())
            } else {
                PokemonSpecies.getByIdentifier(ResourceLocation.fromNamespaceAndPath(args[0].lowercase(), args[1].lowercase()))
            }
        } ?: return GenericResult.invalid(
            CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
        )

        if (lastArgInt != null) {
            val slot = lastArgInt.toIntOrNull()
            if (slot != null) {
                if (slot !in 2 downTo 1)
                    return GenericResult.invalid(
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.abilities.invalidSlot
                    )

                val abilities = species.abilities.toList()

                return GenericResult.valid(
                    if (slot <= abilities.size)
                        Utils.titleCase(abilities[slot - 1].template.name)
                    else
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.abilities.emptySlot
                )
            } else if (lastArgInt.equals("H", true)) {
                for (ability in species.abilities) {
                    if (ability.type is HiddenAbilityType) {
                        return GenericResult.valid(Utils.titleCase(ability.template.name))
                    }
                }
                return GenericResult.valid(
                    CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.abilities.emptySlot
                )
            } else {
                return GenericResult.invalid(
                    CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.abilities.invalidSlot
                )
            }
        }

        return GenericResult.valid(species.abilities.joinToString(", ") { Utils.titleCase(it.template.name) } )
    }

    override fun id(): List<String> = listOf("species_abilities")
    class Options(
        @SerializedName("invalid_slot")
        val invalidSlot: String = "Invalid ability slot argument (1-2,H)!",
        @SerializedName("empty_slot")
        val emptySlot: String = "Empty Ability"
    ) {
        override fun toString(): String {
            return "Options(invalidSlot='$invalidSlot', emptySlot='$emptySlot')"
        }
    }
}
