package com.pokeskies.cobblemonplaceholders.placeholders.types.party

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.cobblemon.mod.common.pokemon.IVs
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.CobblemonPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.Utils
import io.github.miniplaceholders.api.Expansion
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.Tag
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

class PartyIVsPercent : CobblemonPlaceholder {
    override fun register(builder: Expansion.Builder) {
        builder.filter(ServerPlayerEntity::class.java)
            .audiencePlaceholder("party_ivs_percent") { audience, queue, _ ->
                if (queue.peek() == null)
                    return@audiencePlaceholder Tag.inserting(Component.text(
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.invalidSlot
                    ))

                val player = audience as ServerPlayerEntity

                val slot: OptionalInt = queue.pop().asInt()
                if (slot.isEmpty || slot.asInt !in 6 downTo 1)
                    return@audiencePlaceholder Tag.inserting(Component.text(
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.invalidSlot
                    ))

                val pokemon = Cobblemon.storage.getParty(player).get(slot.asInt - 1) 
                    ?: return@audiencePlaceholder Tag.inserting(Component.text(
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.emptySlot
                    ))

                val sum = Stats.PERMANENT.stream().mapToInt { pokemon.ivs[it] ?: 0 }.sum()
                if (sum == 0) return@audiencePlaceholder Tag.inserting(Component.text(sum))

                return@audiencePlaceholder Tag.inserting(Component.text(
                    Utils.parseDouble((sum / (IVs.MAX_VALUE.toDouble() * Stats.PERMANENT.size)) * 100,
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.ivsPercent.fractionMin,
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.ivsPercent.fractionMax
                    )
                ))
            }
    }

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