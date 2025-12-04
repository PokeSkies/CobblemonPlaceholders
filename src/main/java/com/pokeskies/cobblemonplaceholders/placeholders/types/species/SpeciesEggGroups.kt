package com.pokeskies.cobblemonplaceholders.placeholders.types.species

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.Utils
import net.minecraft.resources.ResourceLocation

class SpeciesEggGroups : ServerPlaceholder {
    override fun handle(args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            )

        val species = if (args.size > 1) {
            PokemonSpecies.getByIdentifier(ResourceLocation.fromNamespaceAndPath(args[0].lowercase(), args[1].lowercase()))
        } else {
            PokemonSpecies.getByName(args[0].lowercase())
        } ?: return GenericResult.invalid(
            CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
        )

        if (species.eggGroups.isEmpty())
            return GenericResult.valid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.egggroups.emptyList
            )

        return GenericResult.valid(species.eggGroups.joinToString(", ") { Utils.titleCase(it.name) })
    }

    override fun id(): List<String> = listOf("species_egggroups")
    class Options(
        @SerializedName("empty_list")
        val emptyList: String = "No Egg Groups"
    ) {
        override fun toString(): String {
            return "Options(emptyList='$emptyList')"
        }
    }
}
