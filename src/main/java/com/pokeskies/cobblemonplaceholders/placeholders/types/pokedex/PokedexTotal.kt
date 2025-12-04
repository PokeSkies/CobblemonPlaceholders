package com.pokeskies.cobblemonplaceholders.placeholders.types.pokedex

import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.DexUtils
import net.minecraft.resources.ResourceLocation

class PokedexTotal : ServerPlaceholder {
    override fun handle(args: List<String>): GenericResult {
        var dexID = DexUtils.NAT_DEX_ID
        if (args.size == 1) {
            dexID = ResourceLocation.fromNamespaceAndPath("cobblemon", args[0])
        } else if (args.size >= 2) {
            dexID = ResourceLocation.fromNamespaceAndPath(args[0], args[1])
        }

        val total = DexUtils.getDexTotal(dexID) ?: return GenericResult.invalid("Invalid dex identifier")

        return GenericResult.valid(total.toString())
    }

    override fun id(): List<String> = listOf("pokedex_total")
}
