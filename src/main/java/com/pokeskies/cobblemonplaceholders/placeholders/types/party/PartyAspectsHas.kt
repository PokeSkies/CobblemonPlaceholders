package com.pokeskies.cobblemonplaceholders.placeholders.types.party

import com.cobblemon.mod.common.Cobblemon
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.CobblemonPlaceholder
import io.github.miniplaceholders.api.Expansion
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.Tag
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

class PartyAspectsHas : CobblemonPlaceholder {
    override fun register(builder: Expansion.Builder) {
        builder.filter(ServerPlayerEntity::class.java)
            .audiencePlaceholder("party_aspects_has") { audience, queue, _ ->
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

                if (queue.peek() == null) {
                    return@audiencePlaceholder Tag.inserting(Component.text(
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.aspectsHas.invalidAspect
                    ))
                }

                val aspect = queue.pop().value()
                if (aspect.isEmpty())
                    return@audiencePlaceholder Tag.inserting(Component.text(
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.aspectsHas.invalidAspect
                    ))

                return@audiencePlaceholder Tag.inserting(Component.text(
                    pokemon.aspects.any { it.equals(aspect, true) }
                ))
            }
    }

    class Options(
        @SerializedName("invalid_aspect")
        val invalidAspect: String = "Invalid aspect argument!"
    ) {
        override fun toString(): String {
            return "Options(invalidAspect='$invalidAspect')"
        }
    }
}
