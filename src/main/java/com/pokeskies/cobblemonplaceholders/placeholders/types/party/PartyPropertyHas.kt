package com.pokeskies.cobblemonplaceholders.placeholders.types.party

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.config.ConfigManager
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import net.minecraft.server.level.ServerPlayer

class PartyPropertyHas : PlayerPlaceholder {
    // Args: [slot: Int (1-6), property: String]
    override fun handle(player: ServerPlayer, args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(
                ConfigManager.CONFIG.placeholders.party.invalidSlot
            )

        val slot: Int? = args.getOrNull(0)?.toIntOrNull()
        if (slot == null || slot !in 6 downTo 1)
            return GenericResult.invalid(
                ConfigManager.CONFIG.placeholders.party.invalidSlot
            )

        val pokemon = Cobblemon.storage.getParty(player).get(slot - 1)
            ?: return GenericResult.valid(
                ConfigManager.CONFIG.placeholders.party.emptySlot
            )

        if (args.size < 2) {
            return GenericResult.invalid(
                ConfigManager.CONFIG.placeholders.party.propertyHas.invalidProperty
            )
        }

        val property = args.getOrNull(1)
            ?: return GenericResult.invalid(
                ConfigManager.CONFIG.placeholders.party.propertyHas.invalidProperty
            )

        return GenericResult.valid(PokemonProperties.parse(property).matches(pokemon))
    }

    override fun id(): List<String> = listOf("party_property_has")

    class Options(
        @SerializedName("invalid_property")
        val invalidProperty: String = "Invalid Property"
    ) {
        override fun toString(): String {
            return "Options(invalidProperty='$invalidProperty')"
        }
    }
}
