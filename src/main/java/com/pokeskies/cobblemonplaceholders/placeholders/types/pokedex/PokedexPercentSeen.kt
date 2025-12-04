package com.pokeskies.cobblemonplaceholders.placeholders.types.pokedex

import com.cobblemon.mod.common.api.pokedex.PokedexEntryProgress
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.DexUtils
import com.pokeskies.cobblemonplaceholders.utils.Utils
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

class PokedexPercentSeen : PlayerPlaceholder {
    override fun handle(player: ServerPlayer, args: List<String>): GenericResult {
        var dexID = DexUtils.NAT_DEX_ID
        if (args.size == 1) {
            dexID = ResourceLocation.fromNamespaceAndPath("cobblemon", args[0])
        } else if (args.size >= 2) {
            dexID = ResourceLocation.fromNamespaceAndPath(args[0], args[1])
        }

        val total = DexUtils.getDexTotal(dexID) ?: return GenericResult.invalid("Invalid dex identifier")
        val progress = DexUtils.getDexProgress(
            DexUtils.getDexManager(player),
            dexID,
            PokedexEntryProgress.ENCOUNTERED
        ) ?: return GenericResult.invalid("Invalid dex identifier")

        return GenericResult.valid(Utils.parseDouble(
            (progress.toDouble() / total) * 100,
            CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.pokedex.dexSeenPercent.fractionMin,
            CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.pokedex.dexSeenPercent.fractionMax
        ))
    }

    override fun id(): List<String> = listOf("pokedex_percent_seen")
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
