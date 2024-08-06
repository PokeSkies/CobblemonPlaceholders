package com.pokeskies.cobblemonplaceholders.placeholders.types.party

import com.cobblemon.mod.common.Cobblemon
import com.google.gson.annotations.SerializedName
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.CobblemonPlaceholder
import io.github.miniplaceholders.api.Expansion
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.Tag
import net.minecraft.server.level.ServerPlayer
import java.util.*

class PartyNickname : CobblemonPlaceholder {
    override fun register(builder: Expansion.Builder) {
        builder.filter(ServerPlayer::class.java)
            .audiencePlaceholder("party_nickname") { audience, queue, _ ->
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

                // If nickname is present, return, else return species name if enabled in config
                return@audiencePlaceholder Tag.inserting(Component.text(
                    pokemon.nickname?.string
                        ?: if (CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.nickname.speciesIfEmpty)
                            pokemon.species.name
                        else
                            ""
                    ))
            }
    }

    class Options(
        @SerializedName("species_if_empty")
        val speciesIfEmpty: Boolean = true
    ) {
        override fun toString(): String {
            return "Options(speciesIfEmpty=$speciesIfEmpty)"
        }
    }
}
