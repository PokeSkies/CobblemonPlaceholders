package com.pokeskies.cobblemonplaceholders.placeholders.types.party

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.CobblemonPlaceholder
import io.github.miniplaceholders.api.Expansion
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.Tag
import net.minecraft.server.level.ServerPlayer
import java.util.*

class PartyIVsDefence : CobblemonPlaceholder {
    override fun register(builder: Expansion.Builder) {
        builder.filter(ServerPlayer::class.java)
            .audiencePlaceholder("party_ivs_def") { audience, queue, _ ->
                if (queue.peek() == null)
                    return@audiencePlaceholder Tag.inserting(Component.text(
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.invalidSlot
                    ))

                val player = audience as ServerPlayer

                val slot: OptionalInt = queue.pop().asInt()
                if (slot.isEmpty || slot.asInt !in 6 downTo 1)
                    return@audiencePlaceholder Tag.inserting(Component.text(
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.invalidSlot
                    ))

                val pokemon = Cobblemon.storage.getParty(player).get(slot.asInt - 1) 
                    ?: return@audiencePlaceholder Tag.inserting(Component.text(
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.emptySlot
                    ))

                return@audiencePlaceholder Tag.inserting(Component.text(pokemon.ivs[Stats.DEFENCE] ?: 0))
            }
    }
}
