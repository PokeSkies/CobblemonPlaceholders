package com.pokeskies.cobblemonplaceholders.placeholders.types.party

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.pokeskies.cobblemonplaceholders.placeholders.CobblemonPlaceholder
import io.github.miniplaceholders.api.Expansion
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.Tag
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

class PartyEVsSpecialAttack : CobblemonPlaceholder {
    override fun register(builder: Expansion.Builder) {
        builder.filter(ServerPlayerEntity::class.java)
            .audiencePlaceholder("party_evs_spa") { audience, queue, _ ->
                if (queue.peek() == null)
                    return@audiencePlaceholder Tag.inserting(Component.text("Invalid party slot argument (1-6)!"))

                val player = audience as ServerPlayerEntity

                val slot: OptionalInt = queue.pop().asInt()
                if (slot.isEmpty || slot.asInt !in 6 downTo 1)
                    return@audiencePlaceholder Tag.inserting(Component.text("Invalid party slot argument (1-6)!"))

                val pokemon = Cobblemon.storage.getParty(player).get(slot.asInt - 1) ?: return@audiencePlaceholder Tag.inserting(Component.text("Empty"))

                return@audiencePlaceholder Tag.inserting(Component.text(pokemon.evs[Stats.SPECIAL_ATTACK] ?: 0))
            }
    }
}