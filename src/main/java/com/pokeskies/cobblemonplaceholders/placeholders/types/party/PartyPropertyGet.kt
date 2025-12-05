package com.pokeskies.cobblemonplaceholders.placeholders.types.party

import com.cobblemon.mod.common.Cobblemon
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.config.ConfigManager
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.CustomPropertyType
import net.minecraft.server.level.ServerPlayer

class PartyPropertyGet : PlayerPlaceholder {
    // Args: [slot: Int (1-6), property key: String]
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
                ConfigManager.CONFIG.placeholders.party.aspectsHas.invalidAspect
            )
        }

        val key = args.getOrNull(1)
            ?: return GenericResult.invalid(
                ConfigManager.CONFIG.placeholders.party.aspectsHas.invalidAspect
            )

        pokemon.customProperties.forEach { property ->
            val type = CustomPropertyType.getPropertyType(property) ?: return@forEach
            if (type.id(property).equals(key, true)) {
                return GenericResult.valid(type.value(property))
            }
        }

        return GenericResult.invalid(ConfigManager.CONFIG.placeholders.party.propertyGet.invalidKey)
    }

    override fun id(): List<String> = listOf("party_property_get")

    class Options(
        @SerializedName("invalid_key")
        val invalidKey: String = "No Value"
    ) {
        override fun toString(): String {
            return "Options(invalidKey='$invalidKey')"
        }
    }
}
