package com.pokeskies.cobblemonplaceholders.placeholders.types.party

import com.cobblemon.mod.common.Cobblemon
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import net.minecraft.server.level.ServerPlayer

class PartyAspects : PlayerPlaceholder {
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

        if (args.size > 1) {
            val aspectSlot = args.getOrNull(1)?.toIntOrNull() ?: return GenericResult.invalid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.aspects.invalidSlot
            )

            return GenericResult.valid(
                if (aspectSlot <= pokemon.aspects.size)
                    pokemon.aspects.toList()[aspectSlot - 1]
                else
                    CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.aspects.emptySlot
            )
        }

        if (pokemon.aspects.isEmpty())
            return GenericResult.valid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.aspects.emptyList
            )

        return GenericResult.valid(
            pokemon.aspects.joinToString(", ")
        )
    }

    override fun id(): String = "party_aspects"

    class Options(
        @SerializedName("invalid_slot")
        val invalidSlot: String = "Invalid aspect slot argument (1+)!",
        @SerializedName("empty_list")
        val emptyList: String = "No Aspects",
        @SerializedName("empty_slot")
        val emptySlot: String = "Empty Aspect"
    ) {
        override fun toString(): String {
            return "Options(invalidSlot='$invalidSlot', emptyList='$emptyList', emptySlot='$emptySlot')"
        }
    }
}
