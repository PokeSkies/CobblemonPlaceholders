package com.pokeskies.cobblemonplaceholders.placeholders.types.species

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.pokemon.abilities.HiddenAbilityType
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.CobblemonGlobalPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.Utils
import io.github.miniplaceholders.api.Expansion
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue

class SpeciesAbilities : CobblemonGlobalPlaceholder {
    override fun register(builder: Expansion.Builder) {
        builder.globalPlaceholder("species_abilities", this)
    }

    override fun apply(queue: ArgumentQueue, ctx: Context): Tag {
        if (queue.peek() == null)
            return Tag.inserting(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            ))

        val species = PokemonSpecies.getByName(queue.pop().value().lowercase())
            ?: return Tag.inserting(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            ))

        if (queue.peek() != null) {
            val abilitySlot = queue.pop()
            if (!abilitySlot.asInt().isEmpty) {
                val slot = abilitySlot.asInt().asInt
                if (slot !in 2 downTo 1)
                    return Tag.inserting(Component.text(
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.abilities.invalidSlot
                    ))

                val abilities = species.abilities.toList()

                return Tag.inserting(Component.text(
                    if (slot <= abilities.size)
                        Utils.titleCase(abilities[slot - 1].template.name)
                    else
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.abilities.emptySlot
                ))
            } else if (abilitySlot.value().equals("H", true)) {
                for (ability in species.abilities) {
                    if (ability.type is HiddenAbilityType) {
                        return Tag.inserting(Component.text(Utils.titleCase(ability.template.name)))
                    }
                }
                return Tag.inserting(Component.text(
                    CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.abilities.emptySlot
                ))
            } else {
                return Tag.inserting(Component.text(
                    CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.abilities.invalidSlot
                ))
            }
        }

        return Tag.inserting(Component.text(species.abilities.joinToString(", ") { Utils.titleCase(it.template.name) } ))
    }

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