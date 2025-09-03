package com.pokeskies.cobblemonplaceholders.placeholders.types.pc

import com.cobblemon.mod.common.Cobblemon
import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.GenericResult
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import net.kyori.adventure.text.Component
import net.minecraft.server.level.ServerPlayer

class PCBoxCount : PlayerPlaceholder {
    override fun handle(player: ServerPlayer, args: List<String>): GenericResult {
        return GenericResult.valid(Component.text(Cobblemon.storage.getPC(player).boxes.size))
    }

    override fun id(): String = "pc_box_count"
}
