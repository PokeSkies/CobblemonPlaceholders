package com.pokeskies.cobblemonplaceholders.placeholders.types.party

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.cobblemon.mod.common.pokemon.EVs
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.Utils
import net.minecraft.server.level.ServerPlayer

class PartyEVsPercent : PlayerPlaceholder {
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

        val sum = Stats.PERMANENT.stream().mapToInt { pokemon.evs[it] ?: 0 }.sum()
        if (sum == 0) return GenericResult.valid(sum)

        return GenericResult.valid(
            Utils.parseDouble(
                (sum / EVs.MAX_TOTAL_VALUE.toDouble()) * 100,
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.evsPercent.fractionMin,
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.evsPercent.fractionMax
            )
        )
    }

    override fun id(): List<String> = listOf("party_evs_percent")
    class Options(
        @SerializedName("fraction_min")
        val fractionMin: Int = 0,
        @SerializedName("fraction_max")
        val fractionMax: Int = 0
    ) {
        override fun toString(): String {
            return "Options(fractionMin=$fractionMin, fractionMax=$fractionMax)"
        }
    }
}
