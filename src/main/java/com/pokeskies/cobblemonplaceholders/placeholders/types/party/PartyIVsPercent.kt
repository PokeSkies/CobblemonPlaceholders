package com.pokeskies.cobblemonplaceholders.placeholders.types.party

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.cobblemon.mod.common.pokemon.IVs
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.config.ConfigManager
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.Utils
import net.minecraft.server.level.ServerPlayer

class PartyIVsPercent : PlayerPlaceholder {
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

        val sum = Stats.PERMANENT.stream().mapToInt { pokemon.ivs[it] ?: 0 }.sum()
        if (sum == 0) return GenericResult.valid(sum)

        return GenericResult.valid(
            Utils.parseDouble((sum / (IVs.MAX_VALUE.toDouble() * Stats.PERMANENT.size)) * 100,
                ConfigManager.CONFIG.placeholders.party.ivsPercent.fractionMin,
                ConfigManager.CONFIG.placeholders.party.ivsPercent.fractionMax
            )
        )
    }

    override fun id(): List<String> = listOf("party_ivs_percent")
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
