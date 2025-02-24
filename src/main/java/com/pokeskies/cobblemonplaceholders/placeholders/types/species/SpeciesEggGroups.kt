package com.pokeskies.cobblemonplaceholders.placeholders.types.species

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.Utils
import net.kyori.adventure.text.Component

class SpeciesEggGroups : ServerPlaceholder {
    override fun handle(args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            ))

        val species = PokemonSpecies.getByName(args[0].lowercase())
            ?: return GenericResult.invalid(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.invalidSpecies
            ))

        if (species.eggGroups.isEmpty())
            return GenericResult.valid(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.species.egggroups.emptyList
            ))

        return GenericResult.valid(Component.text(species.eggGroups.joinToString(", ") { Utils.titleCase(it.name) }))
    }

    override fun id(): String = "species_egggroups"

    class Options(
        @SerializedName("empty_list")
        val emptyList: String = "No Egg Groups"
    ) {
        override fun toString(): String {
            return "Options(emptyList='$emptyList')"
        }
    }
}
