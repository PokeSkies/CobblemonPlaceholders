package com.pokeskies.cobblemonplaceholders.placeholders.types.party

import com.cobblemon.mod.common.Cobblemon
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import net.kyori.adventure.text.Component
import net.minecraft.server.level.ServerPlayer

class PartyStatsAttack : PlayerPlaceholder {
    override fun handle(player: ServerPlayer, args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.invalidSlot
            ))

        val slot: Int? = args.getOrNull(0)?.toIntOrNull()
        if (slot == null || slot !in 6 downTo 1)
            return GenericResult.invalid(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.invalidSlot
            ))

        val pokemon = Cobblemon.storage.getParty(player).get(slot - 1)
            ?: return GenericResult.valid(Component.text(
                CobblemonPlaceholders.INSTANCE.configManager.config.placeholders.party.emptySlot
            ))

        return GenericResult.valid(Component.text(pokemon.attack))
    }

    override fun id(): String = "party_stats_atk"
}
