package com.pokeskies.cobblemonplaceholders.placeholders.types.species

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.pokeskies.cobblemonplaceholders.placeholders.CobblemonGlobalPlaceholder
import io.github.miniplaceholders.api.Expansion
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue

class SpeciesMaleRatio : CobblemonGlobalPlaceholder {
    override fun register(builder: Expansion.Builder) {
        builder.globalPlaceholder("species_maleratio", this)
    }

    override fun apply(queue: ArgumentQueue, ctx: Context): Tag {
        if (queue.peek() == null)
            return Tag.inserting(Component.text("Provide a valid species argument!"))

        val species = PokemonSpecies.getByName(queue.pop().value().lowercase()) ?: return Tag.inserting(Component.text("Provide a valid species argument!"))

        return Tag.inserting(Component.text(species.maleRatio))
    }
}