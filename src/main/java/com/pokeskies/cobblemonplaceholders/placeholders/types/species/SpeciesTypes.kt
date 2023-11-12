package com.pokeskies.cobblemonplaceholders.placeholders.types.species

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.CobblemonGlobalPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.Utils
import io.github.miniplaceholders.api.Expansion
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue

class SpeciesTypes : CobblemonGlobalPlaceholder {
    override fun register(builder: Expansion.Builder) {
        builder.globalPlaceholder("species_types", this)
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

        val types = species.types.toList()

        if (queue.peek() != null) {
            val typeSlot = queue.pop().asInt()
            if (typeSlot.isEmpty || typeSlot.asInt !in 2 downTo 1)
                return Tag.inserting(Component.text(
                    CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.types.invalidSlot
                ))

            return Tag.inserting(Component.text(
                if (typeSlot.asInt <= types.size)
                    Utils.titleCase(types[typeSlot.asInt - 1].name)
                else
                    CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.types.emptySlot
            ))
        }

        return Tag.inserting(Component.text(types.joinToString(", ") { Utils.titleCase(it.name) } ))
    }

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