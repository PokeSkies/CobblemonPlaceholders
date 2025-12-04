package com.pokeskies.cobblemonplaceholders.placeholders.types.party

import com.cobblemon.mod.common.Cobblemon
import com.pokeskies.cobblemonplaceholders.config.ConfigManager
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.Utils
import net.minecraft.server.level.ServerPlayer

class PartyCaughtBall : PlayerPlaceholder {
    override fun handle(player: ServerPlayer, args: List<String>): GenericResult {
        if (args.isEmpty())
            return GenericResult.invalid(
                ConfigManager.CONFIG.placeholders.party.invalidSlot
            )

        val slot: Int? = args.getOrNull(0)?.toIntOrNull()
        if (slot == null || slot !in 6 downTo 1)
            return GenericResult.invalid(
                ConfigManager.CONFIG.placeholders.party.invalidSlot
            )

        val pokemon = Cobblemon.storage.getParty(player).get(slot - 1)
            ?: return GenericResult.valid(
                ConfigManager.CONFIG.placeholders.party.emptySlot
            )

        // TODO: Is there a better way to handle this?
        return GenericResult.valid(Utils.titleCase(pokemon.caughtBall.name.path.replace("_", " ")))
    }

    override fun id(): List<String> = listOf("party_caughtball")
}
