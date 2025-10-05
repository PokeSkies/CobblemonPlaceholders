package com.pokeskies.cobblemonplaceholders.placeholders.types.pokedex

import com.cobblemon.mod.common.api.pokedex.PokedexEntryProgress
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.DexUtils
import net.minecraft.server.level.ServerPlayer

class PokedexSpeciesCaught : PlayerPlaceholder {
    override fun handle(player: ServerPlayer, args: List<String>): GenericResult {
        if (args.isEmpty()) return GenericResult.invalid("No species provided!")

        val species = PokemonSpecies.getByName(args[0].lowercase())
            ?: return GenericResult.invalid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.pokedex.invalidSpecies
            )

        val manager = DexUtils.getDexManager(player)

        return GenericResult.valid(manager.getKnowledgeForSpecies(species.resourceIdentifier) == PokedexEntryProgress.CAUGHT)
    }

    override fun id(): String = "pokedex_species_caught"
}
