package com.pokeskies.cobblemonplaceholders.placeholders.types.party

import com.cobblemon.mod.common.Cobblemon
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import net.minecraft.server.level.ServerPlayer

class PartyMoveset : PlayerPlaceholder {
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
            val moveSlot = args.getOrNull(1)?.toIntOrNull()
            if (moveSlot == null || moveSlot !in 4 downTo 1)
                return GenericResult.invalid(
                    CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.moveset.invalidSlot
                )

            return GenericResult.valid(
                pokemon.moveSet[moveSlot - 1]?.displayName?.string
                    ?: CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.moveset.emptySlot
            )
        }

        if (pokemon.moveSet.getMoves().isEmpty())
            return GenericResult.valid(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.moveset.emptyList
            )

        return GenericResult.valid(
            pokemon.moveSet.getMoves().joinToString(", ") { it.displayName.string }
        )
    }

    override fun id(): List<String> = listOf("party_moveset")
    class Options(
        @SerializedName("invalid_slot")
        val invalidSlot: String = "Invalid move slot argument (1-4)!",
        @SerializedName("empty_list")
        val emptyList: String = "No Moves",
        @SerializedName("empty_slot")
        val emptySlot: String = "Empty Move"
    ) {
        override fun toString(): String {
            return "Options(invalidSlot='$invalidSlot', emptyList='$emptyList', emptySlot='$emptySlot')"
        }
    }
}
