package com.pokeskies.cobblemonplaceholders.placeholders.types.party

import com.cobblemon.mod.common.Cobblemon
import com.pokeskies.cobblemonplaceholders.placeholders.CobblemonAudiencePlaceholder
import io.github.miniplaceholders.api.Expansion
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

class PartySpecies : CobblemonAudiencePlaceholder {
    override fun register(builder: Expansion.Builder) {
        builder.filter(ServerPlayerEntity::class.java)
            .audiencePlaceholder("party_species", this)
    }

    override fun tag(audience: Audience, queue: ArgumentQueue, ctx: Context): Tag {
        val player = audience as ServerPlayerEntity

        val slot: OptionalInt = queue.pop().asInt()
        if (slot.isEmpty || slot.asInt !in 6 downTo 1)
            return Tag.inserting(Component.text("Provide a valid slot argument!"))

        val pokemon = Cobblemon.storage.getParty(player).get(slot.asInt - 1) ?: return Tag.inserting(Component.text("Empty"))


        return Tag.inserting(Component.text(pokemon.species.name))
    }
}