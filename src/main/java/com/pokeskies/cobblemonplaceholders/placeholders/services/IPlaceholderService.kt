package com.pokeskies.cobblemonplaceholders.placeholders.services

import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import net.minecraft.server.level.ServerPlayer

interface IPlaceholderService {
    fun registerPlayer(placeholder: PlayerPlaceholder)
    fun registerServer(placeholder: ServerPlaceholder)
    fun finalizeRegister()
    fun parse(input: String, player: ServerPlayer? = null): String
}
