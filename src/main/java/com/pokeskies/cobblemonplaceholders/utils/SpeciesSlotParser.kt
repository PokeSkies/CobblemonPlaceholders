package com.pokeskies.cobblemonplaceholders.utils

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.pokemon.Species
import net.minecraft.resources.ResourceLocation

object SpeciesSlotParser {
    // Helper function to parse a list of arguments for a species and slot index
    fun parseArgs(args: List<String>): Pair<Species?, Int?> {
        if (args.isEmpty()) return null to null
        val lastArgInt = args.lastOrNull()?.toIntOrNull()
        return if (lastArgInt != null && args.size > 1) { // Last arg is slot index
            val species = if (args.size == 2)
                PokemonSpecies.getByName(args[0])
            else
                PokemonSpecies.getByIdentifier(ResourceLocation.fromNamespaceAndPath(args[0], args[1]))
            species to lastArgInt
        } else { // No slot index provided
            val species = if (args.size == 1)
                PokemonSpecies.getByName(args[0])
            else
                PokemonSpecies.getByIdentifier(ResourceLocation.fromNamespaceAndPath(args[0], args[1]))
            species to null
        }
    }
}