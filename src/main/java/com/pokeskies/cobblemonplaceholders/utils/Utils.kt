package com.pokeskies.cobblemonplaceholders.utils

import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minecraft.text.Text


object Utils {
    private val miniMessage: MiniMessage = MiniMessage.miniMessage()

    fun deserializeText(text: String): Text {
        return CobblemonPlaceholders.INSTANCE.adventure!!.toNative(miniMessage.deserialize(text))
    }

    fun printError(message: String) {
        CobblemonPlaceholders.LOGGER.error("[CobblemonPlaceholders] ERROR: $message")
    }

    fun printInfo(message: String) {
        CobblemonPlaceholders.LOGGER.info("[CobblemonPlaceholders] $message")
    }
}