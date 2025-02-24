package com.pokeskies.cobblemonplaceholders.placeholders.services

import com.pokeskies.cobblemonplaceholders.CobblemonPlaceholders
import com.pokeskies.cobblemonplaceholders.placeholders.PlayerPlaceholder
import com.pokeskies.cobblemonplaceholders.placeholders.ServerPlaceholder
import com.pokeskies.cobblemonplaceholders.utils.Utils
import eu.pb4.placeholders.api.PlaceholderResult
import eu.pb4.placeholders.api.Placeholders
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import net.minecraft.resources.ResourceLocation

class PlaceholderAPIService: IPlaceholderService {
    init {
        Utils.printInfo("PlaceholderAPI found, loading placeholders...")
    }

    override fun registerPlayer(placeholder: PlayerPlaceholder) {
        Placeholders.register(ResourceLocation.fromNamespaceAndPath("cobblemon", placeholder.id())) { ctx, arg ->
            val player = ctx.player ?: return@register PlaceholderResult.invalid("NO PLAYER")
            val result = placeholder.handle(player, arg?.split(":") ?: emptyList())
            return@register if (result.isSuccessful) {
                PlaceholderResult.value(CobblemonPlaceholders.INSTANCE.adventure.toNative(result.result))
            } else {
                PlaceholderResult.invalid(PlainTextComponentSerializer.plainText().serialize(result.result))
            }
        }
    }

    override fun registerServer(placeholder: ServerPlaceholder) {
        Placeholders.register(ResourceLocation.fromNamespaceAndPath("cobblemon", placeholder.id())) { ctx, arg ->
            val result = placeholder.handle(arg?.split(":") ?: emptyList())
            return@register if (result.isSuccessful) {
                PlaceholderResult.value(CobblemonPlaceholders.INSTANCE.adventure.toNative(result.result))
            } else {
                PlaceholderResult.invalid(PlainTextComponentSerializer.plainText().serialize(result.result))
            }
        }
    }

    override fun finalizeRegister() {

    }
}
