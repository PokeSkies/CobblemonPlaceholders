package com.pokeskies.cobblemonplaceholders.placeholders.types.party

import com.cobblemon.mod.common.Cobblemon
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import net.minecraft.server.level.ServerPlayer

class PartyNickname : PlayerPlaceholder {
    override fun handle(player: ServerPlayer, args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.invalidSlot
            )

        val slot: Int? = args.getOrNull(0)?.toIntOrNull()
        if (slot == null || slot !in 6 downTo 1)
            return GenericResult.invalid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.invalidSlot
            )

        val pokemon = Cobblemon.storage.getParty(player).get(slot - 1)
            ?: return GenericResult.valid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.emptySlot
            )

        // If nickname is present, return, else return species name if enabled in config
        return GenericResult.valid(
            pokemon.nickname?.string
                ?: if (CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.nickname.speciesIfEmpty)
                    pokemon.species.name
                else
                    ""
        )
    }

    override fun id(): List<String> = listOf("party_nickname")
    class Options(
        @SerializedName("species_if_empty")
        val speciesIfEmpty: Boolean = true
    ) {
        override fun toString(): String {
            return "Options(speciesIfEmpty=$speciesIfEmpty)"
        }
    }
}
