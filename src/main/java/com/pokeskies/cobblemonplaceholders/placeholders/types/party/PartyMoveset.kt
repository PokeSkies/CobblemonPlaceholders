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

class PartyMoveset : CobblemonPlaceholder {
    override fun register(builder: Expansion.Builder) {
        builder.filter(ServerPlayerEntity::class.java)
            .audiencePlaceholder("party_moveset") { audience, queue, _ ->
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
                    val moveSlot = queue.pop().asInt()
                    if (moveSlot.isEmpty || moveSlot.asInt !in 4 downTo 1)
                        return@audiencePlaceholder Tag.inserting(Component.text(
                            CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.moveset.invalidSlot
                        ))

                    return@audiencePlaceholder Tag.inserting(Component.text(
                        pokemon.moveSet[moveSlot.asInt - 1]?.displayName?.string
                            ?: CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.moveset.emptySlot
                    ))
                }

                if (pokemon.moveSet.getMoves().isEmpty())
                    return@audiencePlaceholder Tag.inserting(Component.text(
                        CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.moveset.emptyList
                    ))

                return@audiencePlaceholder Tag.inserting(Component.text(
                    pokemon.moveSet.getMoves().joinToString(", ") { it.displayName.string }
                ))
            }
    }

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