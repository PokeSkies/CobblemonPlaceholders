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

class PartyAspects : CobblemonPlaceholder {
    override fun register(builder: Expansion.Builder) {
        builder.filter(ServerPlayerEntity::class.java)
            .audiencePlaceholder("party_aspects") { audience, queue, _ ->
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

                if (queue.peek() != null) {
                    val aspectSlot = queue.pop().asInt()
                    if (aspectSlot.isEmpty)
                        return@audiencePlaceholder Tag.inserting(Component.text(
                            CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.aspects.invalidSlot
                        ))

                    return@audiencePlaceholder Tag.inserting(Component.text(
                        if (aspectSlot.asInt <= pokemon.aspects.size)
                            pokemon.aspects.toList()[aspectSlot.asInt - 1]
                        else
                            CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.aspects.emptySlot
                    ))
                }

                if (pokemon.aspects.isEmpty())
                    return@audiencePlaceholder Tag.inserting(Component.text(
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.aspects.emptyList
                    ))

                return@audiencePlaceholder Tag.inserting(Component.text(
                    pokemon.aspects.joinToString(", ")
                ))
            }
    }

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
