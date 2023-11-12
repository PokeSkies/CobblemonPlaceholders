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

class SpeciesEggGroups : CobblemonGlobalPlaceholder {
    override fun register(builder: Expansion.Builder) {
        builder.globalPlaceholder("species_egggroups", this)
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

        if (species.eggGroups.isEmpty())
            return Tag.inserting(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.egggroups.emptyList
            ))

        return Tag.inserting(Component.text(species.eggGroups.joinToString(", ") { Utils.titleCase(it.name) }))
    }

    class Options(
        @SerializedName("empty_list")
        val emptyList: String = "No Egg Groups"
    ) {
        override fun toString(): String {
            return "Options(emptyList='$emptyList')"
        }
    }
}