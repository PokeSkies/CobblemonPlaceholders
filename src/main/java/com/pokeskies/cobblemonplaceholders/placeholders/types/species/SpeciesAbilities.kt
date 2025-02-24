package com.pokeskies.cobblemonplaceholders.placeholders.types.species

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.pokemon.abilities.HiddenAbilityType
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.Utils
import net.kyori.adventure.text.Component

class SpeciesAbilities : ServerPlaceholder {
    override fun handle(args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            ))

        val species = PokemonSpecies.getByName(args[0].lowercase())
            ?: return GenericResult.invalid(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            ))

        if (args.size > 1) {
            val abilitySlot = args[1]
            val slot = abilitySlot.toIntOrNull()
            if (slot != null) {
                if (slot !in 2 downTo 1)
                    return GenericResult.invalid(Component.text(
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.abilities.invalidSlot
                    ))

                val abilities = species.abilities.toList()

                return GenericResult.valid(Component.text(
                    if (slot <= abilities.size)
                        Utils.titleCase(abilities[slot - 1].template.name)
                    else
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.abilities.emptySlot
                ))
            } else if (abilitySlot.equals("H", true)) {
                for (ability in species.abilities) {
                    if (ability.type is HiddenAbilityType) {
                        return GenericResult.valid(Component.text(Utils.titleCase(ability.template.name)))
                    }
                }
                return GenericResult.valid(Component.text(
                    CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.abilities.emptySlot
                ))
            } else {
                return GenericResult.invalid(Component.text(
                    CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.abilities.invalidSlot
                ))
            }
        }

        return GenericResult.valid(Component.text(species.abilities.joinToString(", ") { Utils.titleCase(it.template.name) } ))
    }

    override fun id(): String = "species_abilities"

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
