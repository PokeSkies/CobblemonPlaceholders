package com.pokeskies.cobblemonplaceholders.utils

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.pokedex.Dexes
import com.cobblemon.mod.common.api.pokedex.PokedexEntryProgress
import com.cobblemon.mod.common.api.pokedex.PokedexManager
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.pokeskies.cobblemonplaceholders.config.ConfigManager
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

object DexUtils {
    val NAT_DEX_ID: ResourceLocation = ResourceLocation.parse("cobblemon:national")

    fun getDexManager(player: ServerPlayer): PokedexManager {
        return Cobblemon.playerDataManager.getPokedexData(player)
    }

    fun getDexTotal(dexId: ResourceLocation): Int? {
        return Dexes.dexEntryMap[dexId]?.getEntries()?.count { entry ->
            if (ConfigManager.CONFIG.placeholders.pokedex.includeUnimplemented) {
                true
            } else {
                PokemonSpecies.getByIdentifier(entry.speciesId)?.implemented == true
            }
        }
    }

    fun getDexProgress(manager: PokedexManager, dexId: ResourceLocation, knowledge: PokedexEntryProgress): Int? {
        if (Dexes.dexEntryMap.containsKey(dexId).not()) return null
        return Dexes.dexEntryMap[dexId]!!.getEntries().associateBy { it.id }.entries
            .map { it.value }
            .count { manager.getKnowledgeForSpecies(it.speciesId).ordinal >= knowledge.ordinal }
    }

    fun getShinyDexProgress(manager: PokedexManager, dexId: ResourceLocation, knowledge: PokedexEntryProgress): Int? {
        if (Dexes.dexEntryMap.containsKey(dexId).not()) return null
        return Dexes.dexEntryMap[dexId]!!.getEntries().associateBy { it.id }.entries
            .map { it.value }
            .filter { it.conditionAspects.contains("shiny") }
            .count { manager.getKnowledgeForSpecies(it.speciesId).ordinal >= knowledge.ordinal }
    }
}
